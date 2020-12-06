package com.redteamobile.lightning.data.local.sim

import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.SubscriptionInfo
import android.telephony.euicc.DownloadableSubscription
import android.telephony.euicc.EuiccManager
import android.text.TextUtils
import com.redteamobile.ferrari.sim.constants.enums.ProfileState
import com.redteamobile.lightning.data.local.sim.model.ProfileInfos
import com.redteamobile.lightning.util.LogUtil
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author SScience
 * @description
 */
class EuiccController(context: Context) {

    companion object {
        private const val TAG = "EuiccController"
        private const val ACTION_DOWNLOAD_SUBSCRIPTION = "download_subscription"
        const val EMBEDDED_SUBSCRIPTION_RESULT_OK = "0"
        const val EMBEDDED_SUBSCRIPTION_RESULT_NO_CARRIER_PRIVILEGE = "-1"
        const val EMBEDDED_SUBSCRIPTION_RESULT_CANCEL = "-2"
        const val EMBEDDED_SUBSCRIPTION_RESULT_PROFILE_ERROR = "-3"
    }

    private var euiccManager: EuiccManager? = null
    private var context: Context = context.applicationContext
    private val lpaThread = Schedulers.newThread()

    init {
        euiccManager = this.context.getSystemService(Context.EUICC_SERVICE) as EuiccManager?
        if (!euiccManager?.isEnabled!!) {
            LogUtil.e(TAG, "Euicc feature is disabled.")
        }
    }

    fun downloadSubscription(
        activity: Activity,
        ac: String,
        cc: String?,
        switchAfterDownload: Boolean
    ): Observable<String> {
        return Observable.create<String> {
            val sub = DownloadableSubscription.forActivationCode(ac)
            val intent = Intent(ACTION_DOWNLOAD_SUBSCRIPTION)
            val callbackIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )
            var error = false
            val receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    if (ACTION_DOWNLOAD_SUBSCRIPTION != intent.action) {
                        return
                    }
                    val detailCode = intent.getIntExtra(
                        EuiccManager.EXTRA_EMBEDDED_SUBSCRIPTION_DETAILED_CODE, 0
                    )
                    val resultCode = resultCode
                    if (resultCode == EuiccManager.EMBEDDED_SUBSCRIPTION_RESULT_RESOLVABLE_ERROR) {
                        // 没有运营商权限，则弹框确定下载
                        try {
                            euiccManager?.startResolutionActivity(
                                activity,
                                10,
                                intent,
                                callbackIntent
                            )
                        } catch (e: Exception) {
                            LogUtil.e(TAG, "downloadSubscription startResolutionActivity: $e")
                            error = true
                            it.onError(e)
                            context.unregisterReceiver(this)
                        }
                    } else {
                        if (!error) {
                            it.onNext(getEUICCResult(context, resultCode, detailCode))
                            context.unregisterReceiver(this)
                        }
                    }
                    LogUtil.d(TAG, "downloadSubscription result: $resultCode $detailCode")
                }
            }
            context.registerReceiver(receiver, IntentFilter(ACTION_DOWNLOAD_SUBSCRIPTION))

            LogUtil.d(TAG, "downloadSubscription[$ac, $cc, $switchAfterDownload]")
            euiccManager?.downloadSubscription(sub, switchAfterDownload, callbackIntent)
        }
            .compose(CommonTransformer(lpaThread))
    }

    private fun getEUICCResult(context: Context, resultCode: Int, detailCode: Int): String {
        // 0 0 : 成功
        // 2 0 : 资源和App签名不对，没有运营商权限/插实体卡弹框选取消;
        // 2 655362 or xxx : 没有网络;
        // 2 196611 or xxx : Pixel 3XL eSIM 坏了;
        // 2 667648 or xxx : no ara 配置文件不匹配;
        return if (resultCode == 2) {
            if (detailCode == 0) {
                EMBEDDED_SUBSCRIPTION_RESULT_CANCEL
            } else {
                EMBEDDED_SUBSCRIPTION_RESULT_PROFILE_ERROR
            }
        } else {
            EMBEDDED_SUBSCRIPTION_RESULT_OK
        }
    }

    fun getDownloadableSubscriptionList(context: Context): Observable<List<ProfileInfos>> {
        return Observable.create<List<ProfileInfos>> {
            val listSub: List<SubscriptionInfo> =
                TelephonyUtil.getDownloadableSubscriptionList(context)
            val listProfile: ArrayList<ProfileInfos> = ArrayList()
            listSub.forEach { subInfo ->
                val profileInfo = ProfileInfos()
                profileInfo.simSlotIndex = subInfo.simSlotIndex
                profileInfo.iccId = subInfo.iccId
                profileInfo.mccString = subInfo.mccString
                profileInfo.mncString = subInfo.mncString
                profileInfo.carrierName = if (TextUtils.isEmpty(subInfo.carrierName)) "UNKNOWN" else subInfo.carrierName
                profileInfo.subscriptionId = subInfo.subscriptionId
                profileInfo.displayName = subInfo.displayName
                profileInfo.netType = TelephonyUtil.getNetType(context, subInfo.subscriptionId)
                profileInfo.phoneType = TelephonyUtil.getPhoneType(context, subInfo.subscriptionId)
                if (subInfo.simSlotIndex == -1) {
                    profileInfo.profileState = ProfileState.DISABLED.value
                    listProfile.add(profileInfo)
                } else {
                    listProfile.add(0, profileInfo)
                    profileInfo.profileState = ProfileState.ENABLED.value
                }

            }
            val profileInfo = ProfileInfos()
            profileInfo.ac = "1\$rsp.truphone.com\$26E3F6A0C81D4EFA8F1650B0B67C4B1F"
            profileInfo.simSlotIndex = -1
            profileInfo.iccId = "89852240400001279555"
            profileInfo.mccString = "460"
            profileInfo.mncString = "01"
            profileInfo.carrierName = "CHN-UNICOM Redtea"
            profileInfo.subscriptionId = 10
            profileInfo.displayName = "Redtea"
            profileInfo.netType = "NONE"
            profileInfo.phoneType = "NONE"
            profileInfo.profileState = ProfileState.UNDOWNLOAD.value
            listProfile.add(0, profileInfo)
            it.onNext(listProfile)
        }
            .compose(CommonTransformer(lpaThread))
    }

    private class CommonTransformer<T>(val scheduler: Scheduler) : ObservableTransformer<T, T> {

        override fun apply(stream: Observable<T>): ObservableSource<T> {
            return stream.subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}
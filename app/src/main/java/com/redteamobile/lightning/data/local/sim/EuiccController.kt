package com.redteamobile.lightning.data.local.sim

import android.content.Context
import android.telephony.SubscriptionInfo
import android.telephony.euicc.EuiccManager
import android.text.TextUtils
import android.util.Log
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
        private const val TAG = "EUICCUtil"
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
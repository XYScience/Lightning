package com.redteamobile.lightning.ui.profile.adapter

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.redteamobile.lightning.App
import com.redteamobile.lightning.Global
import com.redteamobile.lightning.R
import com.redteamobile.lightning.data.local.sim.EuiccController
import com.redteamobile.lightning.data.local.sim.model.ProfileInfos
import com.redteamobile.lightning.util.LogUtil
import com.redteamobile.lightning.util.UIUtil
import com.redteamobile.lightning.util.Util
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.item_esim_card.view.*
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

/**
 * @author SScience
 * @description
 */
class ESimCardAdapter(val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TAG = "ESimCardAdapter"
    }

    private var dataList: List<ProfileInfos> = ArrayList()

    fun setData(dataList: List<ProfileInfos>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TransRegionalHolder) {
            val any = dataList[position]
            holder.bind(any)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TransRegionalHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_esim_card, parent, false)
        )
    }

    private class TransRegionalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(profileInfo: ProfileInfos) {
            with(itemView) {
                var statusStr: String? = null
                var statusBg: Drawable? = null
                var statusText: Int = -1
                var statusEnable: Boolean = false
                when (profileInfo.profileState) {
                    0 -> {
                        statusStr = "UNUSED"
                        statusBg = resources.getDrawable(R.drawable.shape_status_grey_bg)
                        statusText = resources.getColor(R.color.item_background_grey_text)
                        statusEnable = false
                    }
                    1 -> {
                        statusStr = "USED"
                        statusBg = resources.getDrawable(R.drawable.shape_status_green_bg)
                        statusText = resources.getColor(R.color.item_background_green_text)
                        statusEnable = false
                    }
                    2 -> {
                        statusStr = "UNDOWNLOAD"
                        statusBg = resources.getDrawable(R.drawable.shape_status_yellow_bg)
                        statusText = resources.getColor(R.color.item_background_yellow_text)
                        statusEnable = true
                    }
                }
                tv_status.text = statusStr
                tv_status.background = statusBg
                tv_status.setTextColor(statusText)
                tv_status.isEnabled = statusEnable
                tv_carrier_name.text = profileInfo.carrierName
                tv_iccid.text = "ICCID: ${profileInfo.iccId}"
                tv_mcc.text = "MCC: ${profileInfo.mccString}"
                tv_mnc.text = "MNC: ${profileInfo.mncString}"
                tv_net_type.text = "Net: ${profileInfo.netType}"
                tv_phone_type.text = "Phone: ${profileInfo.phoneType}"

                tv_status.setOnClickListener {
                    downloadProfile(context, profileInfo.ac ?: return@setOnClickListener)
                }
            }
        }

        fun downloadProfile(context: Context, ac: String) {
            if (TextUtils.isEmpty(ac)) {
                return
            }
            LogUtil.d(TAG, "start downloadProfile")

            val loadingDialog =
                UIUtil.showLoading(
                    context as Activity,
                    "Processing.."
                )
            Global.getInstance(App.instance).euiccController
                .downloadSubscription(
                    context as Activity,
                    ac,
                    null,
                    true
                )
                .subscribe(Consumer<String> {
                    loadingDialog?.dismiss()
                    if (it == EuiccController.EMBEDDED_SUBSCRIPTION_RESULT_OK) {
                        Toast.makeText(
                            App.instance,
                            "Succeeded to active eSIM. Please use it later.",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        LogUtil.d(TAG, "downloadProfile failed result: $it")
                        when (it) {
                            EuiccController.EMBEDDED_SUBSCRIPTION_RESULT_NO_CARRIER_PRIVILEGE -> {
                                LogUtil.d(TAG, "downloadProfile failed: no carrier privilege")
                                onShowDialog("App doesn\\'t have carrier privilege to this eSIM.")
                            }
                            EuiccController.EMBEDDED_SUBSCRIPTION_RESULT_CANCEL -> {
                                LogUtil.d(TAG, "downloadProfile cancel download eSIM")
                            }
                            EuiccController.EMBEDDED_SUBSCRIPTION_RESULT_PROFILE_ERROR -> {
                                if (Util.isOnline(App.instance)) {
                                    LogUtil.d(TAG, "downloadProfile failed: profile error")
                                    onShowDialog("Failed to download eSIM")
                                } else {
                                    LogUtil.d(TAG, "downloadProfile failed: no net")
                                    onShowDialog("Request failed, please try again later.")
                                }
                            }
                            else -> {
                                Toast.makeText(
                                    App.instance,
                                    "Request failed, please try again later.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }, Consumer<Throwable> { t ->
                    loadingDialog?.dismiss()
                    LogUtil.d(TAG, "downloadProfile no net")
                    onShowDialog("Request failed, please try again later.")
                })
        }

        private fun onShowDialog(message: String) {

            AlertDialog.Builder(App.instance)
                .setTitle("Tips")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK") { _ , _ ->

                }
        }
    }
}
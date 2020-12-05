package com.redteamobile.lightning.data.local.sim

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.util.Log
import com.redteamobile.lightning.util.LogUtil
import java.lang.Exception

/**
 * @author SScience
 * @description
 */
object TelephonyUtil {

    private const val TAG = "TelephonyUtil"

    fun getDownloadableSubscriptionList(context: Context): List<SubscriptionInfo> {
        return getSubscriptionList(context).filter {
            it.isEmbedded
        }
    }

    fun getSubscriptionList(context: Context): List<SubscriptionInfo> {
        val sm = context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE)
        val cl = Class.forName("android.telephony.SubscriptionManager")
        val method = cl.getDeclaredMethod("getAvailableSubscriptionInfoList")
        var listSub: List<SubscriptionInfo> = emptyList()
        try {
            val result = method.invoke(sm)
            if (result != null) {
                listSub = result as List<SubscriptionInfo>
            }
        } catch (e: Exception) {
            LogUtil.e(">>>>>", "getSubscriptionList Exception: $e")
        }
        return listSub
    }

    fun getTelephonyManager(context: Context?): TelephonyManager? {
        if (context == null) return null
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE)
        return if (telephonyManager != null) (telephonyManager as TelephonyManager) else null
    }

    fun getSubscriptionManager(context: Context?): SubscriptionManager? {
        if (context == null) return null
        val subscriptionManager =
            context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE)
        return if (subscriptionManager != null) (subscriptionManager as SubscriptionManager) else null
    }

    fun getPhoneType(context: Context?, subID: Int): String {
        var phoneTypeStr = ""
        if (context == null) return phoneTypeStr
        try {
            val telephonyManager = getTelephonyManager(context) ?: return phoneTypeStr

            val method = TelephonyManager::class.java.getMethod(
                "getCurrentPhoneType",
                Int::class.javaPrimitiveType
            )
            method.isAccessible = true
            val result: Int =
                method.invoke(telephonyManager, subID) as Int

            phoneTypeStr = when (result) {
                TelephonyManager.PHONE_TYPE_NONE -> //0
                    "NONE"
                TelephonyManager.PHONE_TYPE_GSM -> //1
                    "GSM"
                TelephonyManager.PHONE_TYPE_CDMA -> //2
                    "CDMA"
                TelephonyManager.PHONE_TYPE_SIP -> //3
                    "SIP"
                else -> "UNREGISTER"
            }
        } catch (e: Exception) {
            Log.e(TAG, "getPhoneType", e)
        }
        return phoneTypeStr
    }

    fun getNetType(context: Context?, subID: Int): String {
        if (context == null) return ""
        val telephonyManager = getTelephonyManager(context) ?: return ""
        val method = TelephonyManager::class.java.getMethod(
            "getNetworkTypeName",
            Int::class.javaPrimitiveType
        )
        method.isAccessible = true
        val result = method.invoke(telephonyManager, getNetworkType(context, subID))

        return result?.toString() ?: "--"
    }

    fun getNetworkType(context: Context?, subID: Int): Int {
        if (context == null) return 0
        val telephonyManager = getTelephonyManager(context) ?: return 0
        var result: Int = 0
        try {
            val method =
                TelephonyManager::class.java.getMethod(
                    "getNetworkType",
                    Int::class.javaPrimitiveType
                )
            method.isAccessible = true
            result = method.invoke(telephonyManager, subID) as Int
        } catch (e: Exception) {
            Log.e(TAG, "getNetworkClass", e)
        }
        return result
    }

    @SuppressLint("MissingPermission")
    fun getSubID(context: Context?, slotId: Int): Int {
        val sm = getSubscriptionManager(context)
        val info = sm?.getActiveSubscriptionInfoForSimSlotIndex(slotId)
        return info?.subscriptionId ?: 0
    }
}
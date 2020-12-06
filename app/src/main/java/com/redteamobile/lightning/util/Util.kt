package com.redteamobile.lightning.util

import android.content.Context
import android.net.ConnectivityManager
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author SScience
 * @description
 */
object Util {

    const val TAG = "Util"
    const val DATA_TYPE = "yyyy-MM-dd HH:mm:ss"

    fun generateRequestId(): String {
        val timeStamp: Long = System.currentTimeMillis()
        val randomNum: Long = ((Math.random() * 9 + 1) * 100000).toLong()
        val requestId: String = timeStamp.toString() + randomNum.toString()
        return requestId.md5()
    }

    fun formatTime(time: Long?): String {
        val timeFormat = SimpleDateFormat(
            DATA_TYPE,
            Locale.getDefault(Locale.Category.FORMAT)
        )
        return timeFormat.format(time)
    }

    fun isOnline(context: Context): Boolean {
        val connMgr = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        if (networkInfo == null) {
            LogUtil.d(TAG, "networkInfo is null")
            return false
        }
        val state = networkInfo.state
        if (state == null) {
            LogUtil.d(TAG, "networkInfo state is null")
            return false
        }
        LogUtil.d(TAG, String.format("networkInfo state is %s", state.toString()))
        return networkInfo.isConnected
    }
}

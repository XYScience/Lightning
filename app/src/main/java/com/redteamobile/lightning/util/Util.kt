package com.redteamobile.lightning.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author SScience
 * @description
 */
object Util {

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
}

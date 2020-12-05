package com.redteamobile.lightning.util

/**
 * @author SScience
 * @description
 */
object Util {

    fun generateRequestId(): String {
        val timeStamp: Long = System.currentTimeMillis()
        val randomNum: Long = ((Math.random() * 9 + 1) * 100000).toLong()
        val requestId: String = timeStamp.toString() + randomNum.toString()
        return requestId.md5()
    }
}

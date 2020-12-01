package com.redteamobile.lightning.util

import android.content.Context
import android.util.Log
import com.redteamobile.lightning.BuildConfig

/**
 * @author SScience
 * @description
 */
object LogUtil {

    private const val LOG_APP_TAG = "Lightning_"

    private const val DISABLE: Int = 0
    private const val DEBUG: Int = 1

    private var logMode: Int = DISABLE

    fun initLogMode(context: Context) {
        if (BuildConfig.log) {
            this.logMode = DEBUG
        }
        if (SPUtil.isDebug(context)) {
            this.logMode = DEBUG
        }
    }

    private fun isLoggable(): Boolean {
        return logMode > DISABLE
    }

    fun d(tag: String, msg: String) {
        if (isLoggable()) {
            Log.d(LOG_APP_TAG + tag, msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (isLoggable()) {
            Log.e(LOG_APP_TAG + tag, msg)
        }
    }
}
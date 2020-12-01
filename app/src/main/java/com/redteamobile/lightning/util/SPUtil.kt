package com.redteamobile.lightning.util

import android.content.Context
import android.content.SharedPreferences

/**
 * @author SScience
 * @description
 */
object SPUtil {

    private const val PREF_NAME = "preferences"

    private const val DEBUG = "debug"

    fun getPrefs(context: Context): SharedPreferences {
        var code = Context.MODE_PRIVATE
        code = code or Context.MODE_APPEND
        return context.getSharedPreferences(PREF_NAME, code)
    }

    fun isDebug(context: Context): Boolean {
        return getPrefs(context).getBoolean(DEBUG, false)
    }

    fun setDebug(context: Context, debug: Boolean) {
        getPrefs(context).edit().putBoolean(DEBUG, debug).apply()
    }
}
package com.redteamobile.lightning.data.remote

import android.content.Context
import com.redteamobile.lightning.BuildConfig
import com.redteamobile.lightning.data.remote.interceptor.HeaderInterceptor
import com.redteamobile.lightning.util.LogUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * @author SScience
 * @description
 */

object HttpClient {

    const val TAG = "HttpClient"

    private object Configuration {
        const val CONNECT_TIMEOUT: Long = 10
        const val WRITE_TIMEOUT: Long = 10
        const val READ_TIMEOUT: Long = 30
    }

    fun create(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(Configuration.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Configuration.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Configuration.READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(HeaderInterceptor(context))
            .addInterceptor(HttpLoggingInterceptor { message ->
                LogUtil.d(TAG, message)
            }.setLevel(levelOfLog()))
            .build()
    }

    fun environment(): Int {
        return BuildConfig.serverType
    }

    private fun levelOfLog(): HttpLoggingInterceptor.Level {
        return if (BuildConfig.log) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

}
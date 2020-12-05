package com.redteamobile.lightning.data.remote

import android.content.Context

/**
 * 网络模块入口类。
 * 可控配置在[com.redteamobile.augustus.net.service.HttpClient]中
 */
class HttpManager private constructor(context: Context) {

    companion object {
        private const val TAG = "HttpManager"

        @Volatile
        private var instance: HttpManager? = null

        fun getInstance(context: Context): HttpManager {
            if (instance == null) {
                synchronized(HttpManager::class.java) {
                    if (instance == null) {
                        instance = HttpManager(context)
                    }
                }
            }
            return instance!!
        }
    }
    var httpService = HttpService(context)

}
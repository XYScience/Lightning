package com.redteamobile.lightning.data.local.cache

import android.content.Context

class CacheManager private constructor(context: Context) {

    private val memoryCache = MemoryCache()

    operator fun get(key: String): String? {
        var value: String? = memoryCache[key]
        if (value == null) {
            if (value != null) {
                memoryCache.put(key, value)
            }
        }
        return value
    }

    fun put(key: String, value: String) {
        memoryCache.put(key, value)
    }

    fun remove(key: String) {
        removeMemory(key)
    }

    private fun removeMemory(key: String) {
        memoryCache.remove(key)
    }

    companion object {

        private val LOG_TAG = "CacheManager"
        @Volatile
        private var cacheManager: CacheManager? = null

        fun getInstance(context: Context): CacheManager {
            if (cacheManager == null) {
                synchronized(CacheManager::class.java) {
                    if (cacheManager == null) {
                        cacheManager =
                            CacheManager(context)
                    }
                }
            }
            return cacheManager!!
        }
    }

}

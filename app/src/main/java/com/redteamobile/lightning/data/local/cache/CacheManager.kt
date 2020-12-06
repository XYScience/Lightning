package com.redteamobile.lightning.data.local.cache

import android.content.Context

class CacheManager private constructor(context: Context) {

    private val memoryCache = MemoryCache()
    private val fileCache: FileCache = FileCache(context)

    operator fun get(key: String): String? {
        var value: String? = memoryCache[key]
        if (value == null) {
            value = fileCache[key]
            if (value != null) {
                memoryCache.put(key, value)
            }
        }
        return value
    }

    fun put(key: String, value: String) {
        fileCache.put(key, value)
        memoryCache.put(key, value)
    }

    fun remove(key: String) {
        fileCache.remove(key)
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

package com.redteamobile.lightning.data.local.cache

import android.util.LruCache

class MemoryCache {

    private val mLruCache: LruCache<String, String>

    init {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory / 8
        mLruCache = object : LruCache<String, String>(cacheSize) {
            override fun sizeOf(key: String, value: String): Int {
                return value.toByteArray().size
            }
        }
    }

    operator fun get(key: String): String? {
        return mLruCache.get(key)
    }

    fun put(key: String, value: String) {
        mLruCache.put(key, value)
    }

    fun remove(key: String) {
        mLruCache.remove(key)
    }

}

package com.redteamobile.lightning.data.local.cache

import android.content.Context
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.redteamobile.lightning.data.remote.model.bean.EquipmentModel
import com.redteamobile.lightning.data.remote.model.bean.UserModel
import com.redteamobile.lightning.util.fromJson
import com.redteamobile.lightning.util.toJson

/**
 * @author SScience
 * @description
 */
object LogicCache {

    private const val KEY_USER = "userInfo"
    private const val KEY_EQUIPMENT = "cards"

    fun saveEquipment(context: Context, text: ArrayList<EquipmentModel>?) {
        save(context, KEY_EQUIPMENT, text)
    }

    fun getEquipment(context: Context): ArrayList<EquipmentModel>? {
        val load = load(context, KEY_EQUIPMENT)
        load?: return null
        return Gson().fromJson(load, object : TypeToken<List<EquipmentModel>>(){}.type)
    }

    fun userInfo(context: Context): UserModel? {
        return loadBean(context, KEY_USER, UserModel::class.java)
    }

    fun saveUserInfo(context: Context, obj: UserModel?) {
        save(context, KEY_USER, obj)
    }

    private fun save(context: Context, key: String, value: Any?) {
        val valueStr = value?.toJson()
        if (valueStr.isNullOrBlank()) {
            getCacheManager(context).remove(key)
        } else {
            getCacheManager(context).put(key, valueStr)
        }
    }

    private fun <T> loadBean(context: Context, key: String, clazz: Class<T>): T? {
        return load(context, key)?.fromJson(clazz)
    }

    private fun load(context: Context, key: String): String? {
        return getCacheManager(context)[key]
    }

    private fun getCacheManager(context: Context): CacheManager {
        return CacheManager.getInstance(context)
    }
}

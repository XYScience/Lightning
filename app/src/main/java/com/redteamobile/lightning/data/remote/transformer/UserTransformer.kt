package com.redteamobile.lightning.data.remote.transformer

import android.content.Context
import android.util.Log
import com.redteamobile.lightning.data.local.cache.LogicCache
import com.redteamobile.lightning.data.remote.model.response.TaskResponse
import com.redteamobile.lightning.data.remote.model.response.UserInfoResponse
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

class UserTransformer(var context: Context) :
    ObservableTransformer<UserInfoResponse, UserInfoResponse> {

    override fun apply(upstream: Observable<UserInfoResponse>): ObservableSource<UserInfoResponse> {
        return upstream.doOnNext {
            if (it.success && (it.result != null)) {
                LogicCache.saveUserInfo(context, it.result?.user)
                LogicCache.saveEquipment(context, it.result?.equipmentList)
            }
        }
    }

}
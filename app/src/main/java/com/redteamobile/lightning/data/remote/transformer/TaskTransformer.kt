package com.redteamobile.lightning.data.remote.transformer

import android.content.Context
import com.redteamobile.lightning.data.remote.model.response.BannerResponse
import com.redteamobile.lightning.data.remote.model.response.TaskResponse
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

class TaskTransformer(var context: Context) :
    ObservableTransformer<TaskResponse, TaskResponse> {

    override fun apply(upstream: Observable<TaskResponse>): ObservableSource<TaskResponse> {
        return upstream.doOnNext {
            if (it.success && !it.obj.isNullOrEmpty()) {
            }
        }
    }

}
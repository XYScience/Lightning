package com.redteamobile.lightning.data.remote.transformer

import android.content.Context
import com.redteamobile.lightning.data.remote.model.response.BannerResponse
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

class BannerTransformer(var context: Context) :
    ObservableTransformer<BannerResponse, BannerResponse> {

    override fun apply(upstream: Observable<BannerResponse>): ObservableSource<BannerResponse> {
        return upstream.doOnNext {
            if (it.success && !it.obj.isNullOrEmpty()) {
            }
        }
    }

}
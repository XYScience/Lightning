package com.redteamobile.lightning.data.remote.transformer

import android.content.Context
import com.redteamobile.lightning.data.remote.model.response.BasicResponse
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

class StartTaskTransformer(var context: Context) :
    ObservableTransformer<BasicResponse, BasicResponse> {

    override fun apply(upstream: Observable<BasicResponse>): ObservableSource<BasicResponse> {
        return upstream.doOnNext {
        }
    }

}
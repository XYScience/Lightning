package com.redteamobile.lightning.data.remote.transformer

import com.redteamobile.lightning.data.remote.model.response.BasicResponse
import com.redteamobile.lightning.util.LogUtil
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.schedulers.Schedulers

class RequestTransformer<T : BasicResponse>(private val skipResponseCheck: Boolean = false) :
    ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.subscribeOn(Schedulers.io())
            .doOnNext {
                if (!skipResponseCheck && it is BasicResponse && !it.success) {
                    LogUtil.e("RequestTransformer", "request fail: code: ${it.code}, msg: ${it.msg}")
                }
            }
    }

}

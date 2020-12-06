package com.redteamobile.lightning.data.remote

import android.content.Context
import android.util.Log
import com.redteamobile.lightning.data.remote.api.Api
import com.redteamobile.lightning.data.remote.model.request.BasicRequest
import com.redteamobile.lightning.data.remote.model.request.TaskGetRequest
import com.redteamobile.lightning.data.remote.model.response.BannerResponse
import com.redteamobile.lightning.data.remote.model.response.BasicResponse
import com.redteamobile.lightning.data.remote.model.response.TaskResponse
import com.redteamobile.lightning.data.remote.model.response.UserInfoResponse
import com.redteamobile.lightning.data.remote.transformer.*
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author SScience
 * @description
 */
class HttpService(ctx: Context) {

    companion object {
        private const val TAG = "RedteaService"
    }

    private val context = ctx.applicationContext

    private var redteaGOApi = Retrofit.Builder()
        .client(HttpClient.create(context))
        .baseUrl(baseUrl())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    private fun baseUrl(): String {
        val environment = HttpClient.environment()
        return when (environment) {
            1 -> Api.BaseUrl.QA
            3 -> Api.BaseUrl.PROD
            else -> Api.BaseUrl.PROD
        }
    }

    fun banner(): Observable<BannerResponse> {
        return redteaGOApi.banner(BasicRequest())
            .compose(RequestTransformer<BannerResponse>())
            .compose(BannerTransformer(context))
    }

    fun userInfo(): Observable<UserInfoResponse> {
        return redteaGOApi.userInfo(BasicRequest())
            .compose(RequestTransformer<UserInfoResponse>())
            .compose(UserTransformer(context))
    }

    fun task(): Observable<TaskResponse> {
        return redteaGOApi.task(BasicRequest())
            .compose(RequestTransformer<TaskResponse>())
            .compose(TaskTransformer(context))
    }

    fun startTask(request: TaskGetRequest): Observable<BasicResponse> {
        return redteaGOApi.startTask(request)
            .compose(RequestTransformer<BasicResponse>())
            .compose(StartTaskTransformer(context))
    }

}

package com.redteamobile.lightning.data.remote.api

import com.redteamobile.lightning.data.remote.model.request.BasicRequest
import com.redteamobile.lightning.data.remote.model.request.TaskGetRequest
import com.redteamobile.lightning.data.remote.model.response.BannerResponse
import com.redteamobile.lightning.data.remote.model.response.BasicResponse
import com.redteamobile.lightning.data.remote.model.response.TaskResponse
import com.redteamobile.lightning.data.remote.model.response.UserInfoResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author SScience
 * @description
 */
interface Api {

    object BaseUrl {
        const val PROD = "http://ais.esim.plus/"
        const val QA = "http://2iui24.natappfree.cc/"
    }

    /**
     * test
     */
    @POST("http://ais.esim.plus/v1/query/banner")
    fun banner(@Body request: BasicRequest): Observable<BannerResponse>

    /**
     * UserInfo
     */
    @POST("user/getByUserId?id=2")
    fun userInfo(@Body request: BasicRequest): Observable<UserInfoResponse>

    /**
     * Task
     */
    @POST("user/task/list")
    fun task(@Body request: BasicRequest): Observable<TaskResponse>

    /**
     * Do Task
     */
    @POST("user/task/get")
    fun startTask(@Body request: TaskGetRequest): Observable<BasicResponse>

}

package com.redteamobile.lightning.data.remote.api

import com.redteamobile.lightning.data.remote.model.request.BasicRequest
import com.redteamobile.lightning.data.remote.model.response.BannerResponse
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
        const val QA = "http://13.250.5.222:8087/"
    }

    /**
     * 广告列表
     */
    @POST("v1/query/banner")
    fun banner(@Body request: BasicRequest): Observable<BannerResponse>

}

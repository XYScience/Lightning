package com.redteamobile.lightning.data.remote.interceptor

import android.content.Context
import com.google.common.hash.Hashing
import com.redteamobile.lightning.util.LogUtil
import com.redteamobile.lightning.util.UIUtil
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.util.*

class HeaderInterceptor(var context: Context) : Interceptor {

    companion object {
        const val TAG = "HeaderInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
            .header("Accept-Language", UIUtil.getLocaleDefault())
            .header("Content-Type", "application/json")
            .header("signature", signatureWithBody(request.body()))
        if (request.header("token") != null) {
            // 需要使用token的，给api相应接口的@Headers("")增加"token: "即可
//            val token = LogicCache.token(context)
//            if (token.isNullOrEmpty()) {
//                builder.removeHeader("token")
//            } else {
//                builder.header("token", token.replace("\"", ""))
//            }
        }
        return chain.proceed(builder.build())
    }

    private fun localeForRequest(): String {
        return Locale.getDefault().toString()
    }

    private fun signatureWithBody(body: RequestBody?): String {
        return Hashing.sha1().newHasher().putString(body2String(body), Charsets.UTF_8).hash().toString()
    }

    private fun body2String(requestBody: RequestBody?): String {
        var bodyStr = ""
        if (requestBody != null) {
            val buffer = Buffer()
            try {
                requestBody.writeTo(buffer)
            } catch (e: IOException) {
                LogUtil.e(TAG, "body2String error: $e")
            }
            bodyStr = buffer.readString(Charsets.UTF_8)
            if (!bodyStr.isEmpty()) {
                LogUtil.d(TAG, String.format("--> Body: %s", bodyStr))
            }
        }
        return bodyStr
    }

}

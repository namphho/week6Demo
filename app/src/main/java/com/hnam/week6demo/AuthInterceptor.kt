package com.hnam.week6demo

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by nampham on 5/26/20.
 */
class AuthInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("api_key", MainActivity.API_KEY).build()
        val finalRequest = request.newBuilder().url(url).build()
        return chain.proceed(finalRequest)
    }

}
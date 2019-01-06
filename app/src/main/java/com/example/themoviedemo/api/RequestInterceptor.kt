package com.example.themoviedemo.api

import com.example.themoviedemo.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response



internal class RequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()
        val url = originalUrl.newBuilder()
//                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .addQueryParameter("api_key", "04afe67fd7d505fc8d5925d2a0abbd59")
                .build()

        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}

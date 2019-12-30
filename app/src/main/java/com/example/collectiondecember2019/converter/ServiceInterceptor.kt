package com.example.collectiondecember2019.converter

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class ServiceInterceptor(var context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = if (request.header("No-Auth") == null) {
            val token = "token here"
            request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else
            request.newBuilder().removeHeader("No-Auth").build()

        val response = chain.proceed(request)

        if (response.code() == 401) {
            throw TokenExpired()
        }

        return response
    }
}


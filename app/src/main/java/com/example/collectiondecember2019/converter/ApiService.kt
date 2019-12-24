package com.example.collectiondecember2019.converter

import android.content.Context
import com.example.collectiondecember2019.BuildConfig
import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import vn.aipacific.aihealth.simplifier.converter.ApiConverterFactory
import java.util.concurrent.TimeUnit

object ApiService {

    private var mInstance: ApiEndpoint? = null

    fun getInstance(context: Context): ApiEndpoint {
        if (mInstance == null) {
            val httpClient = OkHttpClient.Builder()
            httpClient.connectTimeout(1, TimeUnit.MINUTES)
            httpClient.readTimeout(1, TimeUnit.MINUTES)
            httpClient.addInterceptor(
                ServiceInterceptor(
                    context
                )
            )

            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                httpClient.addInterceptor(logging)
            }

            val retrofit = Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(ApiConverterFactory(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(httpClient.build())
                .build()

            mInstance = retrofit.create(ApiEndpoint::class.java)
        }
        return mInstance as ApiEndpoint
    }
}
package com.example.collectiondecember2019.first_ex_koin.di.module

import android.content.Context
import com.example.collectiondecember2019.first_ex_koin.api.ServiceMovies
import com.example.collectiondecember2019.first_ex_koin.util.Constants
import com.example.collectiondecember2019.first_ex_koin.view.MovieViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val retrofitModule = module {
    single { okHttp() }
    single { retrofit(Constants.BASE_URL) }
    single { get<Retrofit>().create(ServiceMovies::class.java) }
}

private fun okHttp() = OkHttpClient.Builder().build()

private fun retrofit(baseUrl: String) = Retrofit.Builder()
    .callFactory(OkHttpClient.Builder().build())
    .baseUrl(baseUrl)
    .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
    .build()


val picassoModule = module {
    single {
        picasso(androidContext(), okHttp3Downloader(get()))
    }

}

private fun okHttp3Downloader(client: OkHttpClient) = OkHttp3Downloader(client)

private fun picasso(context: Context, downloader: OkHttp3Downloader) =
    Picasso.Builder(context).downloader(downloader).build()


val movieModule = module {
    viewModel { MovieViewModel(get()) }
}
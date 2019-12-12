package com.example.collectiondecember2019.first_ex_koin.api

import com.example.collectiondecember2019.first_ex_koin.model.MovieCollection
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceMovies {

    @GET(value = "popular")
    suspend fun popularMovies(
        @Query(
            value = "api_key",
            encoded = false
        ) apiKey: String, @Query(value = "page") pageNumber: Int = 1
    ): List<MovieCollection.Movie>
}
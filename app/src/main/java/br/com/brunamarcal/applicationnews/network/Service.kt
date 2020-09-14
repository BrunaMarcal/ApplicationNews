package br.com.brunamarcal.applicationnews.network

import br.com.brunamarcal.applicationnews.model.NewsResponse
import br.com.brunamarcal.applicationnews.model.SourcesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    @GET("top-headlines")
    suspend fun getTopNews(@Query("country") country: String,
                           @Query("apiKey") apiKey: String): NewsResponse

    @GET("everything")
    suspend fun getEverything(@Query("language") language: String,
                              @Query("q") q: String,
                              @Query("apiKey") apiKey: String): NewsResponse

    @GET("sources")
    suspend fun getSources(@Query("category") category: String,
                           @Query("apiKey") apiKey: String): SourcesResponse
}

package br.com.brunamarcal.applicationnews.network

import br.com.brunamarcal.applicationnews.model.NewsResponse
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
}


//https://newsapi.org/v2/everything?language=pt&q=android&apiKey=df600a77399c4f47a3bacc117e19054d

//https://newsapi.org/v2/everything?q=bitcoin&apiKey=df600a77399c4f47a3bacc117e19054d

//v2/everything? q=bitcoin & apiKey=df600a77399c4f47a3bacc117e19054d
package br.com.brunamarcal.applicationnews.network

import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    @GET("v2/top-headlines?")
    suspend fun getTopNews(@Query("q") q: String,
                           @Query("apiKey") apiKey: String)
}




//https://newsapi.org/v2/everything?q=bitcoin&apiKey=df600a77399c4f47a3bacc117e19054d

//v2/everything? q=bitcoin & apiKey=df600a77399c4f47a3bacc117e19054d
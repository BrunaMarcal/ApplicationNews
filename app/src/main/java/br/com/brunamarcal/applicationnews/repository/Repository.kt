package br.com.brunamarcal.applicationnews.repository

import android.content.Context
import br.com.brunamarcal.applicationnews.model.NewsResponse
import br.com.brunamarcal.applicationnews.network.ApiService

class Repository (context: Context){

    suspend fun getTopNews(country: String, apiKey: String): NewsResponse =
        ApiService.service.getTopNews(country, apiKey)

    suspend fun getEverything(language: String, q: String, apiKey: String): NewsResponse =
        ApiService.service.getEverything(language, q, apiKey)

}
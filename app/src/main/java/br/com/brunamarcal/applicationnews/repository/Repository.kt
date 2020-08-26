package br.com.brunamarcal.applicationnews.repository

import br.com.brunamarcal.applicationnews.model.NewsResponse
import br.com.brunamarcal.applicationnews.network.ApiService

class Repository {

    suspend fun getTopNews(country: String, apiKey: String): NewsResponse =
        ApiService.service.getTopNews(country, apiKey)



}
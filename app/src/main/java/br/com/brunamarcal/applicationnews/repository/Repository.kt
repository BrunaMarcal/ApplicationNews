package br.com.brunamarcal.applicationnews.repository

import br.com.brunamarcal.applicationnews.network.ApiService

class Repository {

    suspend fun getTopNews(q: String, apiKey: String) =
        ApiService.service.getTopNews(q, apiKey)

}
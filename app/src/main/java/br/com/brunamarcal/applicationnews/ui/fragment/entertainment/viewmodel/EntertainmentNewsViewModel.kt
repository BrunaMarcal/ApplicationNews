package br.com.brunamarcal.applicationnews.ui.fragment.entertainment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.brunamarcal.applicationnews.core.State
import br.com.brunamarcal.applicationnews.model.NewsResponse
import br.com.brunamarcal.applicationnews.model.SourcesResponse
import br.com.brunamarcal.applicationnews.repository.Repository
import br.com.brunamarcal.applicationnews.ui.fragment.business.viewmodel.BusinessNewsViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EntertainmentNewsViewModel(private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModel() {
    val responseEntertainment = MutableLiveData<State<SourcesResponse>>()

    fun getEntertainmentNews(category: String, apiKey: String) = viewModelScope.launch {
        try {
            responseEntertainment.value = State.loading(true)
            val response = withContext(ioDispatcher){
                repository.getSources(category, apiKey)
            }
            responseEntertainment.postValue(State.success(response))
        } catch (t: Throwable){
            responseEntertainment.postValue(State.error(t))
        }

    }
    class ActionViewModelProviderFactory(private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EntertainmentNewsViewModel::class.java)) {
                return EntertainmentNewsViewModel(repository, ioDispatcher) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")

        }
    }

}
package br.com.brunamarcal.applicationnews.ui.fragment.business.viewmodel

import androidx.lifecycle.*
import br.com.brunamarcal.applicationnews.core.State
import br.com.brunamarcal.applicationnews.model.NewsResponse
import br.com.brunamarcal.applicationnews.model.SourcesResponse
import br.com.brunamarcal.applicationnews.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BusinessNewsViewModel(private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModel() {
    private val responseBusinessLiveData = MutableLiveData<State<SourcesResponse>>()

    val responseBusiness: LiveData<State<SourcesResponse>>
        get() = responseBusinessLiveData

    fun getBusinessNews(category: String, apiKey: String) = viewModelScope.launch {
        try {
            responseBusinessLiveData.value = State.loading(true)
            val response = withContext(ioDispatcher){
                repository.getSources(category, apiKey)
            }
            responseBusinessLiveData.postValue(State.success(response))

        } catch (t: Throwable){
            responseBusinessLiveData.postValue(State.error(t))
        }

    }
    class BusinessNewsModelProviderFactory(private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BusinessNewsViewModel::class.java)) {
                return BusinessNewsViewModel(repository, ioDispatcher) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")

        }
    }

}
package br.com.brunamarcal.applicationnews.ui.activity.home.viewmodel

import androidx.lifecycle.*
import br.com.brunamarcal.applicationnews.core.State
import br.com.brunamarcal.applicationnews.model.NewsResponse
import br.com.brunamarcal.applicationnews.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeNewsViewModel (private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModel(){
    private val newsResponseLiveData = MutableLiveData<State<NewsResponse>>()
    val newsResponse: LiveData<State<NewsResponse>>
        get() = newsResponseLiveData

    fun getTopNews(country: String, apiKey: String) = viewModelScope.launch {
        try {
            newsResponseLiveData.value = State.loading(true)
            val response = withContext(ioDispatcher){
                repository.getTopNews(country, apiKey)
        }
            newsResponseLiveData.postValue(State.success(response))

    } catch (t: Throwable){
            newsResponseLiveData.postValue(State.error(t))
        }
    }

    class HomeNewsViewModelProviderFactory(private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeNewsViewModel::class.java)) {
                return HomeNewsViewModel(repository, ioDispatcher) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
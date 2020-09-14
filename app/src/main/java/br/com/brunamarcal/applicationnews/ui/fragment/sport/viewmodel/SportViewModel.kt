package br.com.brunamarcal.applicationnews.ui.fragment.sport.viewmodel

import androidx.lifecycle.*
import br.com.brunamarcal.applicationnews.core.State
import br.com.brunamarcal.applicationnews.model.SourcesResponse
import br.com.brunamarcal.applicationnews.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SportViewModel  (private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModel() {
    private val responseSportLiveData = MutableLiveData<State<SourcesResponse>>()

    val responseSport: LiveData<State<SourcesResponse>>
        get() = responseSportLiveData

    fun getSportNews(category: String, apiKey: String) = viewModelScope.launch {
        try {
            responseSportLiveData.value = State.loading(true)
            val response = withContext(ioDispatcher){
                repository.getSources(category, apiKey)
            }
            responseSportLiveData.postValue(State.success(response))

        } catch (t: Throwable){
            responseSportLiveData.postValue(State.error(t))
        }

    }
    class SportNewsModelProviderFactory(private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SportViewModel::class.java)) {
                return SportViewModel(repository, ioDispatcher) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")

        }
    }
}

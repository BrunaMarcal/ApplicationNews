package br.com.brunamarcal.applicationnews.ui.fragment.technology.viewmodel

import androidx.lifecycle.*
import br.com.brunamarcal.applicationnews.core.State
import br.com.brunamarcal.applicationnews.model.SourcesResponse
import br.com.brunamarcal.applicationnews.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TechnologyViewModel (private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModel() {
    private val responseTechnologyLiveData = MutableLiveData<State<SourcesResponse>>()

    val responseTechnology: LiveData<State<SourcesResponse>>
        get() = responseTechnologyLiveData

    fun getTechnologyNews(category: String, apiKey: String) = viewModelScope.launch {
        try {
            responseTechnologyLiveData.value = State.loading(true)
            val response = withContext(ioDispatcher){
                repository.getSources(category, apiKey)
            }
            responseTechnologyLiveData.postValue(State.success(response))

        } catch (t: Throwable){
            responseTechnologyLiveData.postValue(State.error(t))
        }

    }
    class TechnologyNewsModelProviderFactory(private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TechnologyViewModel::class.java)) {
                return TechnologyViewModel(repository, ioDispatcher) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")

        }
    }
}

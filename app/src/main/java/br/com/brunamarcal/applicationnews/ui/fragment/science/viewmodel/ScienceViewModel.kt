package br.com.brunamarcal.applicationnews.ui.fragment.science.viewmodel

import androidx.lifecycle.*
import br.com.brunamarcal.applicationnews.core.State
import br.com.brunamarcal.applicationnews.model.SourcesResponse
import br.com.brunamarcal.applicationnews.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScienceViewModel (private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModel() {
    private val responseScienceLiveData = MutableLiveData<State<SourcesResponse>>()

    val responseScience: LiveData<State<SourcesResponse>>
        get() = responseScienceLiveData

    fun getScienceNews(category: String, apiKey: String) = viewModelScope.launch {
        try {
            responseScienceLiveData.value = State.loading(true)
            val response = withContext(ioDispatcher){
                repository.getSources(category, apiKey)
            }
            responseScienceLiveData.postValue(State.success(response))

        } catch (t: Throwable){
            responseScienceLiveData.postValue(State.error(t))
        }

    }
    class ScienceNewsModelProviderFactory(private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ScienceViewModel::class.java)) {
                return ScienceViewModel(repository, ioDispatcher) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")

        }
    }
}

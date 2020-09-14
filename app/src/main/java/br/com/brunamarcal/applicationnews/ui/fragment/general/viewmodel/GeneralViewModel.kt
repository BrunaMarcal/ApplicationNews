package br.com.brunamarcal.applicationnews.ui.fragment.general.viewmodel

import androidx.lifecycle.*
import br.com.brunamarcal.applicationnews.core.State
import br.com.brunamarcal.applicationnews.model.SourcesResponse
import br.com.brunamarcal.applicationnews.repository.Repository
import br.com.brunamarcal.applicationnews.ui.fragment.business.viewmodel.BusinessNewsViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GeneralViewModel(private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModel() {
    private val responseGeneralLiveData = MutableLiveData<State<SourcesResponse>>()

    val responseGeneral: LiveData<State<SourcesResponse>>
        get() = responseGeneralLiveData

    fun getGeneralNews(category: String, apiKey: String) = viewModelScope.launch {
        try {
            responseGeneralLiveData.value = State.loading(true)
            val response = withContext(ioDispatcher){
                repository.getSources(category, apiKey)
            }
            responseGeneralLiveData.postValue(State.success(response))

        } catch (t: Throwable){
            responseGeneralLiveData.postValue(State.error(t))
        }

    }
    class GeneralNewsModelProviderFactory(private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GeneralViewModel::class.java)) {
                return GeneralViewModel(repository, ioDispatcher) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")

        }
    }
}

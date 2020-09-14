package br.com.brunamarcal.applicationnews.ui.fragment.health.viewmodel

import androidx.lifecycle.*
import br.com.brunamarcal.applicationnews.core.State
import br.com.brunamarcal.applicationnews.model.SourcesResponse
import br.com.brunamarcal.applicationnews.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HealthViewModel (private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModel() {
        private val responseHealthLiveData = MutableLiveData<State<SourcesResponse>>()

        val responseHealth: LiveData<State<SourcesResponse>>
            get() = responseHealthLiveData

        fun getHealthNews(category: String, apiKey: String) = viewModelScope.launch {
            try {
                responseHealthLiveData.value = State.loading(true)
                val response = withContext(ioDispatcher){
                    repository.getSources(category, apiKey)
                }
                responseHealthLiveData.postValue(State.success(response))

            } catch (t: Throwable){
                responseHealthLiveData.postValue(State.error(t))
            }

        }
        class HealthNewsModelProviderFactory(private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(HealthViewModel::class.java)) {
                    return HealthViewModel(repository, ioDispatcher) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")

            }
        }
    }
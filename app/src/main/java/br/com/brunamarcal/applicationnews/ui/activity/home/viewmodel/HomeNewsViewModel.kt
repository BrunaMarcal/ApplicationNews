package br.com.brunamarcal.applicationnews.ui.activity.home.viewmodel

import android.util.Log
import androidx.lifecycle.*
import br.com.brunamarcal.applicationnews.core.State
import br.com.brunamarcal.applicationnews.core.Status
import br.com.brunamarcal.applicationnews.model.NewsResponse
import br.com.brunamarcal.applicationnews.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Logger

class HomeNewsViewModel (private val repository: Repository, private val ioDispatcher: CoroutineDispatcher): ViewModel(){
    private val newsResponseLiveData = MutableLiveData<State<NewsResponse>>()
    private val DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

    val newsResponse: LiveData<State<NewsResponse>>
        get() = newsResponseLiveData


    fun dataConversion(s: String?): Date?{
        var date: Date? = null
        try {
            date = SimpleDateFormat(DATE_FORMAT_PATTERN).parse(s)
        } catch (e: ParseException){
            Status.ERROR
        }
        return null
    }

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
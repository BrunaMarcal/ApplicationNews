package br.com.brunamarcal.applicationnews.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import br.com.brunamarcal.applicationnews.BuildConfig
import br.com.brunamarcal.applicationnews.R
import br.com.brunamarcal.applicationnews.core.Status
import br.com.brunamarcal.applicationnews.repository.Repository
import br.com.brunamarcal.applicationnews.ui.home.viewmodel.HomeNewsViewModel
import kotlinx.coroutines.Dispatchers

class HomeNewsActivity : AppCompatActivity() {
    lateinit var viewModel: HomeNewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_news)

        val repository = Repository()
        viewModel = HomeNewsViewModel.HomeNewsViewModelProviderFactory(repository, Dispatchers.IO).create(HomeNewsViewModel::class.java)
        viewModel.getTopNews("us", BuildConfig.API_KEY)

        viewModel.newsResponse.observe(this, Observer {response ->
            when(response.status){
                Status.SUCCESS -> {
                    response.data?.let {
                        Log.d("NEWS", "news -> ${it.newsResult[0].title}")
                    }
                }
                Status.ERROR -> {
                    Log.d("ERROR", "Error -> ${response.error}")
                }
                Status.LOADING -> { }
            }

        })
    }
}

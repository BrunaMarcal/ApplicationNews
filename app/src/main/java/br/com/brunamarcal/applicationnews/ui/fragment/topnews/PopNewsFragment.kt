package br.com.brunamarcal.applicationnews.ui.fragment.topnews

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunamarcal.applicationnews.BuildConfig
import br.com.brunamarcal.applicationnews.R
import br.com.brunamarcal.applicationnews.core.Status
import br.com.brunamarcal.applicationnews.repository.Repository
import br.com.brunamarcal.applicationnews.ui.activity.home.viewmodel.HomeNewsViewModel
import br.com.brunamarcal.applicationnews.ui.adapter.NewsAdapter
import kotlinx.android.synthetic.main.fragment_pop_news.*
import kotlinx.coroutines.Dispatchers

class PopNewsFragment : Fragment(R.layout.fragment_pop_news) {
    lateinit var viewModel: HomeNewsViewModel
    lateinit var repository: Repository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         activity?.let {

             repository = Repository(it)
             viewModel = HomeNewsViewModel.HomeNewsViewModelProviderFactory(repository, Dispatchers.IO).create(HomeNewsViewModel::class.java)

             viewModel.getTopNews("us", BuildConfig.API_KEY)

             viewModel.newsResponse.observe(viewLifecycleOwner, Observer { response ->
                 progressBarPopNews.visibility = if (response.loading == true) View.VISIBLE else View.GONE
                 when(response.status){
                     Status.SUCCESS -> {
                         response.data?.let {
                             with(recyclerPopNews){
                                 layoutManager = LinearLayoutManager(context)
                                 setHasFixedSize(true)
                                 adapter = NewsAdapter(it.newsResult)
                             }
                         }
                     }
                     Status.ERROR -> {
                         Log.d("ERROR", "Error -> ${response.error}")
                     }
                     Status.LOADING -> {
                         progressBarPopNews.visibility = if (response.loading == true) View.VISIBLE else View.GONE
                     }
                 }

             })
         }
    }

}


package br.com.brunamarcal.applicationnews.ui.fragment.entertainment

import android.content.Intent
import android.net.Uri
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
import br.com.brunamarcal.applicationnews.ui.adapter.NewsAdapter
import br.com.brunamarcal.applicationnews.ui.adapter.SourcesAdapter
import br.com.brunamarcal.applicationnews.ui.fragment.entertainment.viewmodel.EntertainmentNewsViewModel
import kotlinx.android.synthetic.main.fragment_entertainment_news.*
import kotlinx.coroutines.Dispatchers

class EntertainmentNewsFragment: Fragment(R.layout.fragment_entertainment_news) {
    lateinit var viewModel: EntertainmentNewsViewModel
    lateinit var repository: Repository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {

            repository = Repository(it)

            viewModel = EntertainmentNewsViewModel.ActionViewModelProviderFactory(repository, Dispatchers.IO).create(EntertainmentNewsViewModel::class.java)

            viewModel.getEntertainmentNews("entertainment", BuildConfig.API_KEY)

            viewModel.responseEntertainment.observe(viewLifecycleOwner, Observer(){ entertainmentResponse ->
                progressBarEntertainment.visibility = if (entertainmentResponse.loading == true) View.VISIBLE else View.GONE
                when (entertainmentResponse.status){
                    Status.SUCCESS -> {
                        entertainmentResponse.data?.let {
                            with (recyclerEntertainment){
                                layoutManager = LinearLayoutManager(context)
                                setHasFixedSize(true)
                                adapter = SourcesAdapter(it.response){
                                    val intent = Intent (Intent.ACTION_VIEW, Uri.parse(it.url))
                                    intent.putExtra("DATA_NEWS", it)
                                    startActivity(intent)
                                }
                            }
                        }
                    }
                    Status.ERROR -> { Log.d("ERROR", "Error -> ${entertainmentResponse.error}")
                }
                    Status.LOADING -> {
                        progressBarEntertainment.visibility = if (entertainmentResponse.loading == true) View.VISIBLE else View.GONE
                    }
                }
            })
        }

    }
}

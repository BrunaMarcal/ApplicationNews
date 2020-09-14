package br.com.brunamarcal.applicationnews.ui.fragment.technology

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
import br.com.brunamarcal.applicationnews.ui.adapter.SourcesAdapter
import br.com.brunamarcal.applicationnews.ui.fragment.technology.viewmodel.TechnologyViewModel
import kotlinx.android.synthetic.main.fragment_technology_news.*
import kotlinx.coroutines.Dispatchers

class TechnologyNewsFragment: Fragment (R.layout.fragment_technology_news) {
    lateinit var viewModel: TechnologyViewModel
    lateinit var repository: Repository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {

            repository = Repository(it)
            viewModel = TechnologyViewModel.TechnologyNewsModelProviderFactory(repository, Dispatchers.IO).create(TechnologyViewModel::class.java)

            viewModel.getTechnologyNews("technology", BuildConfig.API_KEY)

            viewModel.responseTechnology.observe(viewLifecycleOwner, Observer { technologyResponse ->
                progressBarTechnology.visibility = if (technologyResponse.loading == true) View.VISIBLE else View.GONE
                when (technologyResponse.status){
                    Status.SUCCESS -> {
                        technologyResponse.data?.let {
                            with (recyclerTechnology){
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
                    Status.ERROR -> { Log.d("ERROR", "Error -> ${technologyResponse.error}")
                    }
                    Status.LOADING -> {
                        progressBarTechnology.visibility = if (technologyResponse.loading == true) View.VISIBLE else View.GONE
                    }
                }
            })
        }

    }
}

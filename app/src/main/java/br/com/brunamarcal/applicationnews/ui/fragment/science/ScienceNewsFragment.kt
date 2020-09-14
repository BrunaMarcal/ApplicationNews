package br.com.brunamarcal.applicationnews.ui.fragment.science

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
import br.com.brunamarcal.applicationnews.ui.fragment.science.viewmodel.ScienceViewModel
import kotlinx.android.synthetic.main.fragment_science_news.*
import kotlinx.coroutines.Dispatchers

class ScienceNewsFragment: Fragment(R.layout.fragment_science_news) {
    lateinit var viewModel: ScienceViewModel
    lateinit var repository: Repository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {

            repository = Repository(it)
            viewModel = ScienceViewModel.ScienceNewsModelProviderFactory(repository, Dispatchers.IO).create(ScienceViewModel::class.java)

            viewModel.getScienceNews("science", BuildConfig.API_KEY)

            viewModel.responseScience.observe(viewLifecycleOwner, Observer { scienceResponse ->
                progressBarScience.visibility = if (scienceResponse.loading == true) View.VISIBLE else View.GONE
                when (scienceResponse.status){
                    Status.SUCCESS -> {
                        scienceResponse.data?.let {
                            with (recyclerScience){
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
                    Status.ERROR -> { Log.d("ERROR", "Error -> ${scienceResponse.error}")
                    }
                    Status.LOADING -> {
                        progressBarScience.visibility = if (scienceResponse.loading == true) View.VISIBLE else View.GONE
                    }
                }
            })
        }

    }
}

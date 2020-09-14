package br.com.brunamarcal.applicationnews.ui.fragment.general

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
import br.com.brunamarcal.applicationnews.ui.fragment.general.viewmodel.GeneralViewModel
import kotlinx.android.synthetic.main.fragment_business_news.progressBarBusiness
import kotlinx.android.synthetic.main.fragment_general_news.*
import kotlinx.coroutines.Dispatchers

class GeneralNewsFragment: Fragment(R.layout.fragment_general_news) {
    lateinit var viewModel: GeneralViewModel
    lateinit var repository: Repository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {

            repository = Repository(it)
            viewModel = GeneralViewModel.GeneralNewsModelProviderFactory(repository, Dispatchers.IO).create(GeneralViewModel::class.java)

            viewModel.getGeneralNews("general", BuildConfig.API_KEY)

            viewModel.responseGeneral.observe(viewLifecycleOwner, Observer { generalResponse ->
                progressBarBusiness.visibility = if (generalResponse.loading == true) View.VISIBLE else View.GONE
                when (generalResponse.status){
                    Status.SUCCESS -> {
                        generalResponse.data?.let {
                            with (recyclerGeneral){
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
                    Status.ERROR -> { Log.d("ERROR", "Error -> ${generalResponse.error}")
                    }
                    Status.LOADING -> {
                        progressBarBusiness.visibility = if (generalResponse.loading == true) View.VISIBLE else View.GONE
                    }
                }
            })
        }

    }
}

package br.com.brunamarcal.applicationnews.ui.fragment.sport

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import br.com.brunamarcal.applicationnews.R
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunamarcal.applicationnews.BuildConfig
import br.com.brunamarcal.applicationnews.core.Status
import br.com.brunamarcal.applicationnews.repository.Repository
import br.com.brunamarcal.applicationnews.ui.adapter.SourcesAdapter
import br.com.brunamarcal.applicationnews.ui.fragment.sport.viewmodel.SportViewModel
import kotlinx.android.synthetic.main.fragment_sport_news.*
import kotlinx.coroutines.Dispatchers


class SportNewsFragment: Fragment(R.layout.fragment_sport_news) {
    lateinit var viewModel: SportViewModel
    lateinit var repository: Repository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {

            repository = Repository(it)
            viewModel = SportViewModel.SportNewsModelProviderFactory(repository, Dispatchers.IO).create(SportViewModel::class.java)

            viewModel.getSportNews("sports", BuildConfig.API_KEY)

            viewModel.responseSport.observe(viewLifecycleOwner, Observer { sportResponse ->
                progressBarSport.visibility = if (sportResponse.loading == true) View.VISIBLE else View.GONE
                when (sportResponse.status){
                    Status.SUCCESS -> {
                        sportResponse.data?.let {
                            with (recyclerSport){
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
                    Status.ERROR -> { Log.d("ERROR", "Error -> ${sportResponse.error}")
                    }
                    Status.LOADING -> {
                        progressBarSport.visibility = if (sportResponse.loading == true) View.VISIBLE else View.GONE
                    }
                }
            })
        }

    }
}

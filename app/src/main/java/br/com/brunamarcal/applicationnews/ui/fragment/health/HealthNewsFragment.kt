package br.com.brunamarcal.applicationnews.ui.fragment.health

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
import br.com.brunamarcal.applicationnews.ui.fragment.health.viewmodel.HealthViewModel
import kotlinx.android.synthetic.main.fragment_business_news.*
import kotlinx.android.synthetic.main.fragment_general_news.*
import kotlinx.android.synthetic.main.fragment_health_news.*
import kotlinx.coroutines.Dispatchers

class HealthNewsFragment : Fragment(R.layout.fragment_health_news) {
lateinit var viewModel: HealthViewModel
lateinit var repository: Repository

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    activity?.let {

        repository = Repository(it)
        viewModel = HealthViewModel.HealthNewsModelProviderFactory(repository, Dispatchers.IO).create(HealthViewModel::class.java)

        viewModel.getHealthNews("health", BuildConfig.API_KEY)

        viewModel.responseHealth.observe(viewLifecycleOwner, Observer { healthResponse ->
            progressBarHealth.visibility = if (healthResponse.loading == true) View.VISIBLE else View.GONE
            when (healthResponse.status){
                Status.SUCCESS -> {
                    healthResponse.data?.let {
                        with (recyclerHealth){
                            layoutManager = LinearLayoutManager(context)
                            setHasFixedSize(true)
                            adapter = SourcesAdapter(it.response) {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                                    intent.putExtra("DATA_NEWS", it)
                                    startActivity(intent)
                                }
                        }
                    }
                }
                Status.ERROR -> { Log.d("ERROR", "Error -> ${healthResponse.error}")
                }
                Status.LOADING -> {
                    progressBarHealth.visibility = if (healthResponse.loading == true) View.VISIBLE else View.GONE
                }
            }
        })
    }

}
}

package br.com.brunamarcal.applicationnews.ui.fragment.business

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.brunamarcal.applicationnews.BuildConfig
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunamarcal.applicationnews.R
import br.com.brunamarcal.applicationnews.core.Status
import br.com.brunamarcal.applicationnews.repository.Repository
import br.com.brunamarcal.applicationnews.ui.adapter.NewsAdapter
import br.com.brunamarcal.applicationnews.ui.adapter.SourcesAdapter
import br.com.brunamarcal.applicationnews.ui.fragment.business.viewmodel.BusinessNewsViewModel
import kotlinx.android.synthetic.main.fragment_business_news.*
import kotlinx.coroutines.Dispatchers

class BusinessNewsFragment: Fragment(R.layout.fragment_business_news) {
    lateinit var viewModel: BusinessNewsViewModel
    lateinit var repository: Repository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {

            repository = Repository(it)
            viewModel = BusinessNewsViewModel.BusinessNewsModelProviderFactory(repository, Dispatchers.IO).create(BusinessNewsViewModel::class.java)

            viewModel.getBusinessNews("business", BuildConfig.API_KEY)

            viewModel.responseBusiness.observe(viewLifecycleOwner, Observer { businessResponse ->
                progressBarBusiness.visibility = if (businessResponse.loading == true) View.VISIBLE else View.GONE
                when (businessResponse.status){
                    Status.SUCCESS -> {
                        businessResponse.data?.let {
                            with (recyclerBusiness){
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
                    Status.ERROR -> { Log.d("ERROR", "Error -> ${businessResponse.error}")
                }
                    Status.LOADING -> {
                        progressBarBusiness.visibility = if (businessResponse.loading == true) View.VISIBLE else View.GONE
                    }
                }
            })
        }

    }
}

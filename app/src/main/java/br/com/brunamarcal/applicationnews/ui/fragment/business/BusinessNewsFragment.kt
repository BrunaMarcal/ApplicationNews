package br.com.brunamarcal.applicationnews.ui.fragment.business

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.brunamarcal.applicationnews.ui.activity.home.HomeNewsActivity
import br.com.brunamarcal.applicationnews.ui.activity.home.viewmodel.HomeNewsViewModel

class BusinessNewsFragment: Fragment() {
    lateinit var viewModel: HomeNewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeNewsActivity).viewModel
    }
}
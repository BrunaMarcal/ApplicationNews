package br.com.brunamarcal.applicationnews.ui.activity.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.brunamarcal.applicationnews.R
import br.com.brunamarcal.applicationnews.pageadapter.HomeNewsPageAdapter
import br.com.brunamarcal.applicationnews.repository.Repository
import br.com.brunamarcal.applicationnews.ui.activity.home.viewmodel.HomeNewsViewModel
import br.com.brunamarcal.applicationnews.ui.fragment.topnews.PopNewsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home_news.*
import kotlinx.coroutines.Dispatchers

class HomeNewsActivity : AppCompatActivity(){

    private val fragmentAdapter = HomeNewsPageAdapter (supportFragmentManager)

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {item ->
        when (item.itemId){
            R.id.favoriteFragment-> {
                startActivity((Intent(this@HomeNewsActivity, PopNewsFragment::class.java)))
                return@OnNavigationItemSelectedListener true
            }
            else -> false
        }
    }

    lateinit var viewModel: HomeNewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_news)

        val repository = Repository(this)
        viewModel = HomeNewsViewModel.HomeNewsViewModelProviderFactory(repository, Dispatchers.IO).create(HomeNewsViewModel::class.java)

        viewPagerMain.adapter = fragmentAdapter
        tabsMain.setupWithViewPager(viewPagerMain)

        val bottomNavigation: BottomNavigationView = bottomNavigation
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }
}

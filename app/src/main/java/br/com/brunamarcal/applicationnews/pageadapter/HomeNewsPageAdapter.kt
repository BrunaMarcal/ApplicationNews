package br.com.brunamarcal.applicationnews.pageadapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.com.brunamarcal.applicationnews.ui.fragment.everythingnews.EverythingNewsFragment
import br.com.brunamarcal.applicationnews.ui.fragment.topnews.PopNewsFragment


class HomeNewsPageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> PopNewsFragment()
            1 -> EverythingNewsFragment()
            else -> PopNewsFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "Top Headlines"
            1 -> "Headlines"
            else -> null
        }
    }
}

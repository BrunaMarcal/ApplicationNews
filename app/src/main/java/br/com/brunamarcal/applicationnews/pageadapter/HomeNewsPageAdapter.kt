package br.com.brunamarcal.applicationnews.pageadapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.com.brunamarcal.applicationnews.ui.fragment.business.BusinessNewsFragment
import br.com.brunamarcal.applicationnews.ui.fragment.national.NationalNewsFragment
import br.com.brunamarcal.applicationnews.ui.fragment.politic.PoliticNewsFragment
import br.com.brunamarcal.applicationnews.ui.fragment.topnews.PopNewsFragment


class HomeNewsPageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> PopNewsFragment()
            1 -> NationalNewsFragment()
            2 -> PoliticNewsFragment()
            3 -> BusinessNewsFragment()
            else -> PopNewsFragment()
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "Top News"
            1 -> "National"
            2 -> "Politic"
            3 -> "Business"
            else -> null
        }
    }
}

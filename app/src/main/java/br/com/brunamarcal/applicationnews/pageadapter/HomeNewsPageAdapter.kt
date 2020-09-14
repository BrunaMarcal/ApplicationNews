package br.com.brunamarcal.applicationnews.pageadapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.com.brunamarcal.applicationnews.ui.fragment.business.BusinessNewsFragment
import br.com.brunamarcal.applicationnews.ui.fragment.entertainment.EntertainmentNewsFragment
import br.com.brunamarcal.applicationnews.ui.fragment.general.GeneralNewsFragment
import br.com.brunamarcal.applicationnews.ui.fragment.health.HealthNewsFragment
import br.com.brunamarcal.applicationnews.ui.fragment.science.ScienceNewsFragment
import br.com.brunamarcal.applicationnews.ui.fragment.sport.SportNewsFragment
import br.com.brunamarcal.applicationnews.ui.fragment.technology.TechnologyNewsFragment
import br.com.brunamarcal.applicationnews.ui.fragment.topnews.PopNewsFragment


class HomeNewsPageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> PopNewsFragment()
            1 -> ScienceNewsFragment()
            2 -> EntertainmentNewsFragment()
            3 -> BusinessNewsFragment()
            4 -> HealthNewsFragment()
            5 -> TechnologyNewsFragment()
            6 -> SportNewsFragment()
            7 -> GeneralNewsFragment()
            else -> PopNewsFragment()
        }
    }

    override fun getCount(): Int {
        return 8
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "Top News"
            1 -> "Science"
            2 -> "Entertainment"
            3 -> "Business"
            4 -> "Health"
            5 -> "Technology"
            6 -> "Sports"
            7 -> "General"
            else -> null
        }
    }
}

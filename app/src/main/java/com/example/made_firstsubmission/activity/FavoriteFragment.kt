package com.example.made_firstsubmission.activity


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager

import com.example.made_firstsubmission.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_favorite.view.*

class FavoriteFragment : Fragment() {

    private lateinit var movieTitle : String
    private lateinit var tvTitle : String
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewOfLayout = inflater.inflate(R.layout.fragment_favorite, container, false)

        movieTitle = viewOfLayout.movieTitle.text.toString()
        tvTitle = viewOfLayout.tvTitle.text.toString()

        tabLayout = viewOfLayout.favoriteTabs
        viewPager = viewOfLayout.favoriteViewPager
        setupViewPager(viewPager!!)


        tabLayout!!.setupWithViewPager(viewPager)
        return viewOfLayout
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(FavoriteMovieFragment(), movieTitle)
        adapter.addFragment(FavoriteTVFragment(), tvTitle)
        viewPager.adapter = adapter
    }

    inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val fragmentList = ArrayList<Fragment>()
        private val fragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return fragmentTitleList[position]
        }
    }
}

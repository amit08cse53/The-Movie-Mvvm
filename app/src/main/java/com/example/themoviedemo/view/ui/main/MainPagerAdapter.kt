package com.example.themoviedemo.view.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter



class MainPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> return MovieListFragment()
            1 -> return TvListFragment()
            else -> return PersonListFragment()
        }
    }

    override fun getCount() = 3
}

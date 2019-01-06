package com.example.themoviedemo.utils

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.themoviedemo.R
import devlight.io.library.ntb.NavigationTabBar



object MainNavigationUtil {

    private fun getNavigationModels(context: Context): ArrayList<NavigationTabBar.Model> {
        val colors = context.resources.getStringArray(R.array.default_preview)
        val models = ArrayList<NavigationTabBar.Model>()
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(context, R.drawable.ic_movie_filter_white_24dp),
                        Color.parseColor(colors[0]))
                        .title("movie")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(context, R.drawable.ic_live_tv_white_24dp),
                        Color.parseColor(colors[1]))
                        .title(context.getString(R.string.menu_tv))
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(context, R.drawable.ic_star_white_24dp),
                        Color.parseColor(colors[2]))
                        .title(context.getString(R.string.menu_star))
                        .build()
        )
        return models
    }

    fun setComponents(context: Context, viewPager: ViewPager, navigationTabBar: NavigationTabBar) {
        navigationTabBar.models = this.getNavigationModels(context)
        navigationTabBar.setViewPager(viewPager, 0)
    }
}

package com.example.themoviedemo.di

import com.example.themoviedemo.view.ui.details.movie.MovieDetailActivity
import com.example.themoviedemo.view.ui.details.person.PersonDetailActivity
import com.example.themoviedemo.view.ui.details.tv.TvDetailActivity
import com.example.themoviedemo.view.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector



@Suppress("unused")
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [MainActivityFragmentModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun contributeMovieDetailActivity(): MovieDetailActivity

    @ContributesAndroidInjector
    internal abstract fun contributeTvDetailActivity(): TvDetailActivity

    @ContributesAndroidInjector
    internal abstract fun contributePersonDetailActivity(): PersonDetailActivity
}

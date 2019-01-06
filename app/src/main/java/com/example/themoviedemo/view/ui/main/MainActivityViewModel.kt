package com.example.themoviedemo.view.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.themoviedemo.models.entity.Movie
import com.example.themoviedemo.models.entity.Person
import com.example.themoviedemo.models.Resource
import com.example.themoviedemo.models.entity.Tv
import com.example.themoviedemo.repository.DiscoverRepository
import com.example.themoviedemo.repository.PeopleRepository
import com.example.themoviedemo.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject



class MainActivityViewModel @Inject
constructor(private val discoverRepository: DiscoverRepository, private val peopleRepository: PeopleRepository): ViewModel() {

    private var moviePageLiveData: MutableLiveData<Int> = MutableLiveData()
    private val movieListLiveData: LiveData<Resource<List<Movie>>>

    private var tvPageLiveData: MutableLiveData<Int> = MutableLiveData()
    private val tvListLiveData: LiveData<Resource<List<Tv>>>

    private var peoplePageLiveData: MutableLiveData<Int> = MutableLiveData()
    private val peopleLiveData: LiveData<Resource<List<Person>>>

    init {
        Timber.d("injection MainActivityViewModel")

        movieListLiveData = Transformations.switchMap(moviePageLiveData) {
            moviePageLiveData.value?.let { discoverRepository.loadMovies(it) } ?:
            AbsentLiveData.create()
        }

        tvListLiveData = Transformations.switchMap(tvPageLiveData) {
            tvPageLiveData.value?.let { discoverRepository.loadTvs(it) } ?:
            AbsentLiveData.create()
        }

        peopleLiveData = Transformations.switchMap(peoplePageLiveData) {
            peoplePageLiveData.value?.let { peopleRepository.loadPeople(it) } ?:
            AbsentLiveData.create()
        }
    }

    fun getMovieListObservable() = movieListLiveData
    fun getMovieListValues() = getMovieListObservable().value
    fun postMoviePage(page: Int) = moviePageLiveData.postValue(page)

    fun getTvListObservable() = tvListLiveData
    fun getTvListValues() = getTvListObservable().value
    fun postTvPage(page: Int) = tvPageLiveData.postValue(page)

    fun getPeopleObservable() = peopleLiveData
    fun getPeopleValues() = getPeopleObservable().value
    fun postPeoplePage(page: Int) = peoplePageLiveData.postValue(page)
}

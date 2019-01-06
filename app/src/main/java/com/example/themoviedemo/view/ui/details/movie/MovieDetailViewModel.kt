package com.example.themoviedemo.view.ui.details.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.themoviedemo.models.Keyword
import com.example.themoviedemo.models.Resource
import com.example.themoviedemo.models.Review
import com.example.themoviedemo.models.Video
import com.example.themoviedemo.repository.MovieRepository
import com.example.themoviedemo.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject


class MovieDetailViewModel @Inject
constructor(private val repository: MovieRepository): ViewModel() {

    private val keywordIdLiveData: MutableLiveData<Int> = MutableLiveData()
    private val keywordListLiveData: LiveData<Resource<List<Keyword>>>

    private val videoIdLiveData: MutableLiveData<Int> = MutableLiveData()
    private val videoListLiveData: LiveData<Resource<List<Video>>>

    private val reviewIdLiveData: MutableLiveData<Int> = MutableLiveData()
    private val reviewListLiveData: LiveData<Resource<List<Review>>>

    init {
        Timber.d("Injection MovieDetailViewModel")

        keywordListLiveData = Transformations.switchMap(keywordIdLiveData) {
            keywordIdLiveData.value?.let { repository.loadKeywordList(it) } ?:
                    AbsentLiveData.create()
        }

        videoListLiveData = Transformations.switchMap(videoIdLiveData) {
            videoIdLiveData.value?.let { repository.loadVideoList(it) } ?:
                    AbsentLiveData.create()
        }

        reviewListLiveData = Transformations.switchMap(reviewIdLiveData) {
            reviewIdLiveData.value?.let { repository.loadReviewsList(it) } ?:
                    AbsentLiveData.create()
        }
    }

    fun getKeywordListObservable() = keywordListLiveData
    fun postKeywordId(id: Int) = keywordIdLiveData.postValue(id)

    fun getVideoListObservable() = videoListLiveData
    fun postVideoId(id: Int) = videoIdLiveData.postValue(id)

    fun getReviewListObservable() = reviewListLiveData
    fun postReviewId(id: Int) = reviewIdLiveData.postValue(id)
}

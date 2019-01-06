package com.example.themoviedemo.view.ui.details.tv

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.themoviedemo.R
import com.example.themoviedemo.api.Api
import com.example.themoviedemo.extension.*
import com.example.themoviedemo.models.*
import com.example.themoviedemo.models.entity.Tv
import com.example.themoviedemo.utils.KeywordListMapper
import com.example.themoviedemo.view.adapter.ReviewListAdapter
import com.example.themoviedemo.view.adapter.VideoListAdapter
import com.example.themoviedemo.view.viewholder.VideoListViewHolder
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_tv_detail.*
import kotlinx.android.synthetic.main.layout_detail_body.*
import kotlinx.android.synthetic.main.layout_detail_header.*
import org.jetbrains.anko.toast
import javax.inject.Inject



class TvDetailActivity : AppCompatActivity(), VideoListViewHolder.Delegate {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(TvDetailViewModel::class.java) }

    private val videoAdapter by lazy { VideoListAdapter(this) }
    private val reviewAdapter by lazy { ReviewListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_detail)

        initializeUI()
        observeViewModel()
    }

    private fun initializeUI() {
        applyToolbarMargin(tv_detail_toolbar)
        simpleToolbarWithHome(tv_detail_toolbar, getTvFromIntent().name)
        getTvFromIntent().backdrop_path?.let {
            Glide.with(this).load(Api.getBackdropPath(it))
                    .listener(requestGlideListener(tv_detail_poster))
                    .into(tv_detail_poster)
        } ?: let {
            Glide.with(this).load(Api.getBackdropPath(getTvFromIntent().poster_path))
                    .listener(requestGlideListener(tv_detail_poster))
                    .into(tv_detail_poster)
        }

        detail_header_title.text = getTvFromIntent().name
        detail_header_release.text = "First Air Date : ${getTvFromIntent().first_air_date}"
        detail_header_star.rating = getTvFromIntent().vote_average / 2
        detail_body_recyclerView_trailers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        detail_body_recyclerView_trailers.adapter = videoAdapter
        detail_body_summary.text = getTvFromIntent().overview
        detail_body_recyclerView_reviews.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        detail_body_recyclerView_reviews.adapter = reviewAdapter
        detail_body_recyclerView_reviews.isNestedScrollingEnabled = false
        detail_body_recyclerView_reviews.setHasFixedSize(true)
    }

    private fun observeViewModel() {
        observeLiveData(viewModel.getKeywordListObservable()) { updateKeywordList(it) }
        viewModel.postKeywordId(getTvFromIntent().id)

        observeLiveData(viewModel.getVideoListObservable()) { updateVideoList(it) }
        viewModel.postVideoId(getTvFromIntent().id)

        observeLiveData(viewModel.getReviewListObservable()) { updateReviewList(it) }
        viewModel.postReviewId(getTvFromIntent().id)
    }

    private fun updateKeywordList(resource: Resource<List<Keyword>>) {
        when(resource.status) {
            Status.SUCCESS -> {
                detail_body_tags.tags = KeywordListMapper.mapToStringList(resource.data!!)

                if(resource.data.isNotEmpty()) {
                    detail_body_tags.visible()
                }
            }
            Status.ERROR -> toast(resource.errorEnvelope?.status_message.toString())
            Status.LOADING -> { }
        }
    }

    private fun updateVideoList(resource: Resource<List<Video>>) {
        when(resource.status) {
            Status.SUCCESS -> {
                videoAdapter.addVideoList(resource)

                if(resource.data?.isNotEmpty()!!) {
                    detail_body_trailers.visible()
                    detail_body_recyclerView_trailers.visible()
                }
            }
            Status.ERROR -> toast(resource.errorEnvelope?.status_message.toString())
            Status.LOADING -> { }
        }
    }

    private fun updateReviewList(resource: Resource<List<Review>>) {
        when(resource.status) {
            Status.SUCCESS -> {
                reviewAdapter.addReviewList(resource)

                if(resource.data?.isNotEmpty()!!) {
                    detail_body_reviews.visible()
                    detail_body_recyclerView_reviews.visible()
                }
            }
            Status.ERROR -> toast(resource.errorEnvelope?.status_message.toString())
            Status.LOADING -> { }
        }
    }

    private fun getTvFromIntent(): Tv {
        return intent.getParcelableExtra("tv") as Tv
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == android.R.id.home) onBackPressed()
        return false
    }

    override fun onItemClicked(video: Video) {
        val playVideoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Api.getYoutubeVideoPath(video.key)))
        startActivity(playVideoIntent)
    }
}

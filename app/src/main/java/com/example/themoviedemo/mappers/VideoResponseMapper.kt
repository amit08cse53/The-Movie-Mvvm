package com.example.themoviedemo.mappers

import com.example.themoviedemo.models.network.VideoListResponse



class VideoResponseMapper: NetworkResponseMapper<VideoListResponse> {
    override fun onLastPage(response: VideoListResponse): Boolean {
        return true
    }
}

package com.example.themoviedemo.models.network

import com.example.themoviedemo.models.NetworkResponseModel
import com.example.themoviedemo.models.Video


data class VideoListResponse(val id: Int,
                             val results: List<Video>): NetworkResponseModel

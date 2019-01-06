package com.example.themoviedemo.models.network

import com.example.themoviedemo.models.NetworkResponseModel
import com.example.themoviedemo.models.entity.Tv



data class DiscoverTvResponse(val page: Int,
                              val results: List<Tv>,
                              val total_results: Int,
                              val total_pages: Int): NetworkResponseModel
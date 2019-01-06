package com.example.themoviedemo.models.network

import com.example.themoviedemo.models.NetworkResponseModel
import com.example.themoviedemo.models.entity.Movie



data class DiscoverMovieResponse(val page: Int,
                                 val results: List<Movie>,
                                 val total_results: Int,
                                 val total_pages: Int): NetworkResponseModel

package com.example.themoviedemo.models.network

import com.example.themoviedemo.models.NetworkResponseModel
import com.example.themoviedemo.models.Review


class ReviewListResponse(val id: Int,
                         val page: Int,
                         val results: List<Review>,
                         val total_pages: Int,
                         val total_results: Int): NetworkResponseModel

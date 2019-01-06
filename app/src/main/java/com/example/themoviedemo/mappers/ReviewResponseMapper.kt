package com.example.themoviedemo.mappers

import com.example.themoviedemo.models.network.ReviewListResponse


class ReviewResponseMapper: NetworkResponseMapper<ReviewListResponse> {
    override fun onLastPage(response: ReviewListResponse): Boolean {
        return true
    }
}

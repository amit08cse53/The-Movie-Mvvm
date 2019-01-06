package com.example.themoviedemo.mappers

import com.example.themoviedemo.models.network.KeywordListResponse

class KeywordResponseMapper: NetworkResponseMapper<KeywordListResponse> {
    override fun onLastPage(response: KeywordListResponse): Boolean {
        return true
    }
}

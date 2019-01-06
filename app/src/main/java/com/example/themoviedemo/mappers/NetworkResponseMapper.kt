package com.example.themoviedemo.mappers

import com.example.themoviedemo.models.NetworkResponseModel



interface NetworkResponseMapper<in FROM: NetworkResponseModel> {
    fun onLastPage(response: FROM): Boolean
}

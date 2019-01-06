package com.example.themoviedemo.models.network

import com.example.themoviedemo.models.Keyword
import com.example.themoviedemo.models.NetworkResponseModel

data class KeywordListResponse(val id: Int,
                               val keywords: List<Keyword>): NetworkResponseModel

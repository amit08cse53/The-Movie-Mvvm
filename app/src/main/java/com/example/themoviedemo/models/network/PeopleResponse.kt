package com.example.themoviedemo.models.network

import com.example.themoviedemo.models.NetworkResponseModel
import com.example.themoviedemo.models.entity.Person



data class PeopleResponse(val page: Int,
                          val results: List<Person>,
                          val total_results: Int,
                          val total_pages: Int): NetworkResponseModel

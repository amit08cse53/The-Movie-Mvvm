package com.example.themoviedemo.mappers

import com.example.themoviedemo.models.network.PersonDetail

class PersonDetailResponseMapper: NetworkResponseMapper<PersonDetail> {
    override fun onLastPage(response: PersonDetail): Boolean {
        return true
    }
}

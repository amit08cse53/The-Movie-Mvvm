package com.example.themoviedemo.view.ui.details.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.themoviedemo.models.Resource
import com.example.themoviedemo.models.network.PersonDetail
import com.example.themoviedemo.repository.PeopleRepository
import com.example.themoviedemo.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject


class PersonDetailViewModel @Inject
constructor(private val repository: PeopleRepository): ViewModel() {

    private val personIdLiveData: MutableLiveData<Int> = MutableLiveData()
    private val personLiveData: LiveData<Resource<PersonDetail>>

    init {
        Timber.d("Injection : PersonDetailViewModel")

        personLiveData = Transformations.switchMap(personIdLiveData) {
            personIdLiveData.value?.let { repository.loadPersonDetail(it) } ?:
                    AbsentLiveData.create()
        }
    }

    fun getPersonObservable() = personLiveData
    fun postPersonId(id: Int) = personIdLiveData.postValue(id)
}

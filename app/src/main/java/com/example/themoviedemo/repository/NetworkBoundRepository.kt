package com.example.themoviedemo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.example.themoviedemo.api.ApiResponse
import com.example.themoviedemo.mappers.NetworkResponseMapper
import com.example.themoviedemo.models.NetworkResponseModel
import com.example.themoviedemo.models.Resource
import timber.log.Timber

/**
 * Created by skydoves on 2018. 8. 9.
 * Copyright (c) 2018 skydoves All rights reserved.
 */

abstract class NetworkBoundRepository<ResultType, RequestType: NetworkResponseModel, Mapper: NetworkResponseMapper<RequestType>>
internal constructor() {

    private val result: MediatorLiveData<Resource<ResultType>> = MediatorLiveData()

    init {
        Timber.d("Injection NetworkBoundRepository")
        val loadedFromDB = this.loadFromDb()
        result.addSource(loadedFromDB) { data ->
            result.removeSource(loadedFromDB)
            if (shouldFetch(data)) {
                result.postValue(Resource.loading(null))
                fetchFromNetwork(loadedFromDB)
            } else {
                result.addSource<ResultType>(loadedFromDB) { newData -> setValue(Resource.success(newData, false)) }
            }
        }
    }

    private fun fetchFromNetwork(loadedFromDB: LiveData<ResultType>) {
        val apiResponse = fetchService()
        result.addSource(apiResponse) { response ->
            response?.let {
                when(response.isSuccessful) {
                    true -> {
                        response.body?.let {
                            saveFetchData(it)
                            val loaded = loadFromDb()
                            result.addSource(loaded) { newData -> newData?.let {
                                    setValue(Resource.success(newData, mapper().onLastPage(response.body)))
                                }
                            }
                        }
                    }
                    false -> {
                        result.removeSource(loadedFromDB)
                        onFetchFailed(response.message)
                        response.message?.let {
                            result.addSource<ResultType>(loadedFromDB) {
                                newData -> setValue(Resource.error(it, newData)) }
                        }
                    }
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        result.value = newValue
    }

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

    @WorkerThread
    protected abstract fun saveFetchData(items: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun fetchService(): LiveData<ApiResponse<RequestType>>

    @MainThread
    protected abstract fun mapper(): Mapper

    @MainThread
    protected abstract fun onFetchFailed(message: String?)
}

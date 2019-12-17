package com.example.githubviewer.data.repository

import androidx.annotation.WorkerThread
import com.example.githubviewer.data.remote.response.RepPrResponse
import com.example.githubviewer.data.remote.response.SearchRepResponse
import com.example.githubviewer.data.remote.service.GithubService
import com.example.githubviewer.data.statushandler.Resource
import com.example.githubviewer.extension.networkCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import retrofit2.Retrofit

class RepRepository(private val retrofit: Retrofit, private val githubService: GithubService) {

    @WorkerThread
    suspend fun getRepBySearch(keyword: String, page: Int): Flow<Resource<SearchRepResponse>> = flow {
        emit(retrofit.networkCall {
            githubService.getSearchRepo(keyword, "stars", page).await()
        })
    }.onStart {
        emit(Resource.loading(true))
    }.onCompletion {
        emit(Resource.loading(false))
    }

    @WorkerThread
    suspend fun getRepPr(user: String, rep: String): Flow<Resource<List<RepPrResponse>>> = flow {
        emit(retrofit.networkCall {
            githubService.getRepoPr(user, rep).await()
        })
    }.onStart {
        emit(Resource.loading(true))
    }.onCompletion {
        emit(Resource.loading(false))
    }

}
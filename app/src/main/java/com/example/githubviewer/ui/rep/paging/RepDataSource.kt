package com.example.githubviewer.ui.rep.paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.githubviewer.data.model.Repo
import com.example.githubviewer.data.remote.response.SearchRepResponse
import com.example.githubviewer.data.repository.RepRepository
import com.example.githubviewer.data.statushandler.Status
import com.example.githubviewer.exception.ErrorException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RepDataSource(
    private val repRepository: RepRepository,
    private val scope: CoroutineScope,
    private val dataLoading: MutableLiveData<Boolean>,
    private val errorHandler: MutableLiveData<ErrorException>
) : PageKeyedDataSource<Int, Repo>() {

    private val keyword = "language:kotlin"

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Repo>
    ) {

        scope.launch {
            repRepository.getRepBySearch(keyword, 1).collect {
                when (it.status) {
                    is Status.Success -> {

                        callback.onResult(
                            it.data?.repoList ?: listOf(),
                            1,
                            2
                        )
                    }
                    is Status.Error -> {
                        errorHandler.postValue(it.status.exception)
                    }
                    is Status.Loading -> {
                        dataLoading.postValue(it.status.isLoading)
                    }
                }
            }
        }
    }



    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {
        scope.launch {
            repRepository.getRepBySearch(keyword, params.key).collect {
                when (it.status) {
                    is Status.Success -> {

                        callback.onResult(
                            it.data?.repoList ?: listOf(),
                            params.key.plus(1)
                        )
                    }
                    is Status.Error -> {
                        errorHandler.postValue(it.status.exception)
                    }
                    is Status.Loading -> {
                        dataLoading.postValue(it.status.isLoading)
                    }
                }
            }
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {

    }
}
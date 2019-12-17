package com.example.githubviewer.ui.rep.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.githubviewer.data.model.Repo
import com.example.githubviewer.data.repository.RepRepository
import com.example.githubviewer.exception.ErrorException
import com.example.githubviewer.ui.rep.view.RepDataSource

class RepSearchViewModel(private val repRepository: RepRepository) : ViewModel() {

    private val dataLoading = MutableLiveData<Boolean>()
    val dataLoadingLiveData: LiveData<Boolean> = dataLoading

    private val rep = MutableLiveData<PagedList<Repo>>()
    var repLiveData: LiveData<PagedList<Repo>> = rep

    private val errorHandler = MutableLiveData<ErrorException>()
    val errorHandlerLiveData: LiveData<ErrorException> = errorHandler


    fun initializedPagedListBuilder() {

        val dataSourceFactory = object : DataSource.Factory<Int, Repo>() {
            override fun create(): DataSource<Int, Repo> {
                return RepDataSource(
                    repRepository,
                    viewModelScope,
                    dataLoading,
                    errorHandler
                )
            }
        }

        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .build()

        repLiveData = LivePagedListBuilder(dataSourceFactory, config).build()
    }
}
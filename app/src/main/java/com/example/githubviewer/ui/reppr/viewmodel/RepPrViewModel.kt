package com.example.githubviewer.ui.reppr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubviewer.R
import com.example.githubviewer.data.remote.response.RepPrResponse
import com.example.githubviewer.data.repository.RepRepository
import com.example.githubviewer.data.statushandler.Status
import com.example.githubviewer.exception.ErrorException
import com.example.githubviewer.exception.GenericException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RepPrViewModel(private val repRepository: RepRepository) : ViewModel() {

    private val dataLoading = MutableLiveData<Boolean>()
    val dataLoadingLiveData: LiveData<Boolean> = dataLoading

    private val pr = MutableLiveData<List<RepPrResponse>>()
    var prLiveData: LiveData<List<RepPrResponse>> = pr

    private val errorHandler = MutableLiveData<ErrorException>()
    val errorHandlerLiveData: LiveData<ErrorException> = errorHandler


    fun getRepPr(user: String, rep: String) {
        viewModelScope.launch {
            repRepository.getRepPr(user, rep).collect { res ->
                when (res.status) {
                    is Status.Success -> {
                        if (res.data.isNullOrEmpty()) {
                            errorHandler.postValue(GenericException(R.string.error_empty_pr))
                        } else {
                            pr.postValue(res.data)
                        }
                    }
                    is Status.Loading -> {
                        dataLoading.postValue(res.status.isLoading)
                    }
                    is Status.Error -> {
                        errorHandler.postValue(res.status.exception)
                    }
                }
            }
        }
    }
}
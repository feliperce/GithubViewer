package com.example.githubviewer.exception

import com.example.githubviewer.data.remote.response.ErrorResponse
import com.example.githubviewer.exception.ErrorException

class ServiceException(msg: Int, errorResponse: ErrorResponse? = null) :
    ErrorException(msg, errorResponse)
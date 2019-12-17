package com.example.githubviewer.exception

import com.example.githubviewer.data.remote.response.ErrorResponse
import java.lang.Exception

open class ErrorException(val msg: Int = -1, val errorResponse: ErrorResponse? = null) : Exception()
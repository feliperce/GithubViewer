package com.example.githubviewer.extension

import com.example.githubviewer.R
import com.example.githubviewer.data.remote.response.ErrorResponse
import com.example.githubviewer.exception.GenericException
import com.example.githubviewer.data.statushandler.Resource
import com.example.githubviewer.exception.ConnectionTimeoutException
import com.example.githubviewer.exception.NoConnectionException
import com.example.githubviewer.exception.ServiceException
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


inline fun <reified T> Retrofit.networkCall(service: Retrofit.() -> Response<T>): Resource<T> {

    return try {

        val response = service()

        if (response.isSuccessful) {
            Resource.success(response.body())
        } else {

            val errorBody = response.errorBody()

            if (errorBody == null) {
                Resource.error(GenericException(R.string.error_generic))
            } else {
                val errorConverter: Converter<ResponseBody, ErrorResponse> =
                    this.responseBodyConverter(ErrorResponse::class.java, arrayOf())

                val error = errorConverter.convert(errorBody)
                Resource.error(ServiceException(-1, error))
            }

        }
    } catch (e: Exception) {

        when (e) {
            is UnknownHostException -> {
                Resource.error(NoConnectionException(R.string.no_connection))
            }
            is TimeoutException -> {
                Resource.error(ConnectionTimeoutException(R.string.error_timeout))
            }
            else -> Resource.error(GenericException(R.string.error_generic))
        }
    }
}
package com.example.githubviewer.data.remote.response

import com.example.githubviewer.data.model.Error
import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("documentation_url") val documentationUrl: String,
    @SerializedName("errors") val errorList: List<Error>,
    val message: String
)
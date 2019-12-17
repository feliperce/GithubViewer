package com.example.githubviewer.extension

import android.content.Context
import com.example.githubviewer.exception.ErrorException

fun Context.getErrorStringOrNull(errorException: ErrorException): String? {
    return if (errorException.msg != -1 && errorException.errorResponse != null) {
        errorException.errorResponse.message
    } else if (errorException.msg != -1) {
        this.getString(errorException.msg)
    } else {
        null
    }
}
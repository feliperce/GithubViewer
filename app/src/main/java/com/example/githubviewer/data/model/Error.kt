package com.example.githubviewer.data.model

data class Error(
    val code: String,
    val `field`: String,
    val message: String,
    val resource: String
)
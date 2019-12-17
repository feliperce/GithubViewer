package com.example.githubviewer.data.remote.response

import com.example.githubviewer.data.model.Repo
import com.google.gson.annotations.SerializedName

data class SearchRepResponse(
    val incomplete_results: Boolean,
    @SerializedName("items") val repoList: List<Repo>,
    @SerializedName("total_count") val totalCount: Int
)
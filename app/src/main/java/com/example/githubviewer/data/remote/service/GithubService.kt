package com.example.githubviewer.data.remote.service

import com.example.githubviewer.data.remote.response.RepPrResponse
import com.example.githubviewer.data.remote.response.SearchRepResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    companion object {
        const val REP_SEARCH_PATH = "search/repositories"
        const val REP_PR_PATH = "repos/{user}/{repo}/pulls"
    }

    @GET(REP_SEARCH_PATH)
    fun getSearchRepo(
        @Query("q") keyword: String,
        @Query("sort") sort: String,
        @Query("page") page: Int
    ): Deferred<Response<SearchRepResponse>>

    @GET(REP_PR_PATH)
    fun getRepoPr(
        @Path("user") user: String,
        @Path("repo") repo: String): Deferred<Response<List<RepPrResponse>>>
}

// Snailclimb/JavaGuide/pulls
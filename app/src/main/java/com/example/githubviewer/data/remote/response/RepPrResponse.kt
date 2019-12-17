package com.example.githubviewer.data.remote.response

import com.example.githubviewer.data.model.User
import com.google.gson.annotations.SerializedName

data class RepPrResponse(
    @SerializedName("author_association") val authorAssociation: String,
    val body: String,
    @SerializedName("closed_at") val closedAt: Any,
    @SerializedName("comments_url") val commentsUrl: String,
    @SerializedName("commits_url") val commitsUrl: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("diff_url") val diffUrl: String,
    @SerializedName("html_url") val htmlUrl: String,
    val id: Int,
    @SerializedName("issue_url") val issueUrl: String,
    val locked: Boolean,
    val node_id: String,
    val number: Int,
    @SerializedName("patch_url") val patchUrl: String,
    @SerializedName("review_comment_url") val reviewCommentUrl: String,
    @SerializedName("review_comments_url") val reviewCommentsUrl: String,
    val state: String,
    @SerializedName("statuses_url") val statusesUrl: String,
    val title: String,
    @SerializedName("updated_at") val updatedAt: String,
    val url: String,
    val user: User
)
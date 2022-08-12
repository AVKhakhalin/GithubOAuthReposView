package com.github.oauth.repositories.githuboauthreposview.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubRepoModel(
    @Expose
    val id: String,
    @Expose
    val name: String,
    @Expose
    val description: String,
    @Expose
    val owner: GithubRepoOwnerModel,
    @Expose
    val branches_url: String,
    @Expose
    val forksCount: Int,
    @Expose
    val watchers_count: Int
): Parcelable

@Parcelize
data class GithubRepoOwnerModel(
    @Expose
    val id: String,
    @Expose
    val login: String,
    @Expose
    val avatar_url: String
): Parcelable
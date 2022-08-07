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
    val owner: GithubRepoOwnerModel,
    @Expose
    val forksCount: Int
): Parcelable

@Parcelize
data class GithubRepoOwnerModel(
    @Expose
    val id: String,
    @Expose
    val login: String,
    @Expose
    val avatarUrl: String
): Parcelable
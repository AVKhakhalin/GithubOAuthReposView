package com.github.oauth.repositories.githuboauthreposview.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubCommitModel(
    @Expose
    val sha: String,
    @Expose
    val owner: GithubCommitCommitInfoModel
): Parcelable

@Parcelize
data class GithubCommitCommitInfoModel(
    @Expose
    val message: String,
    @Expose
    val author: RoomGithubCommitInfoAuthorModel
): Parcelable

@Parcelize
data class RoomGithubCommitInfoAuthorModel(
    @Expose
    val name: String,
    @Expose
    val date: String
): Parcelable
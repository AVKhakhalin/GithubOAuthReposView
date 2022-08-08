package com.github.oauth.repositories.githuboauthreposview.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubBrancheModel(
    @Expose
    val name: String
): Parcelable
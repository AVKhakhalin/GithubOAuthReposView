package com.github.oauth.repositories.githuboauthreposview.db.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomGithubUser(
    @NonNull
    @PrimaryKey
    val id: String,
    val login: String,
    val avatarUrl: String,
    val reposUrl: String,
)
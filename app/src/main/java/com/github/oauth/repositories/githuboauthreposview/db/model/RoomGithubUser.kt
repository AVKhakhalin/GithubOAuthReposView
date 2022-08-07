package com.github.oauth.repositories.githuboauthreposview.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomGithubUser(
    @PrimaryKey val id: String,
    val login: String,
    val avatarUrl: String,
    val reposUrl: String,
)
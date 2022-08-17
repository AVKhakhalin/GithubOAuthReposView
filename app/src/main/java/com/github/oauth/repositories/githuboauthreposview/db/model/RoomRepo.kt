package com.github.oauth.repositories.githuboauthreposview.db.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity
data class RoomRepo(
    @NonNull
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val userId: String,
    val forksCount: Int,
    val watchers: Int,
    val login: String,
    val avatarUrl: String,
    val branchesUrl: String
)
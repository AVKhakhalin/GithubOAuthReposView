package com.github.oauth.repositories.githuboauthreposview.db.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity
data class RoomCommit(
    @NonNull
    @PrimaryKey
    val id: String,
    val repoName: String,
    val userLogin: String,
    val message: String,
    val authorName: String,
    val date: String
)
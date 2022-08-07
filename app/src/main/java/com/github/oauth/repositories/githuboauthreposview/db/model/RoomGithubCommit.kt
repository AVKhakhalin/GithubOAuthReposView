package com.github.oauth.repositories.githuboauthreposview.db.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = RoomGithubRepo::class,
            parentColumns = ["id"],
            childColumns = ["repoId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RoomGithubCommit(
    @PrimaryKey val id: String,
    val repoId: String,
    val sha: String,
    val message: String,
    val name: String,
    val date: String
)
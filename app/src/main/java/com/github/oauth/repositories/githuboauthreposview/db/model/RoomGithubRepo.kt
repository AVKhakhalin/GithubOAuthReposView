package com.github.oauth.repositories.githuboauthreposview.db.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = RoomGithubUser::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RoomGithubRepo(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val userId: String,
    val forksCount: Int,
    val watchers: Int,
    val login: String,
    val avatarUrl: String,
    val branches_url: String // TODO: Отрезать с конца строки: {/branch}
)
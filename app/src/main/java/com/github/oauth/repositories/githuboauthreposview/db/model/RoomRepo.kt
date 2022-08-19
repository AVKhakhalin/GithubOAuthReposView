package com.github.oauth.repositories.githuboauthreposview.db.model

import androidx.annotation.NonNull
import androidx.room.*

@Entity(foreignKeys = [
    ForeignKey(
        entity = RoomUser::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RoomRepo(
    @NonNull
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    @NonNull
    @ColumnInfo(index = true)
    val userId: String,
    val forksCount: Int,
    val watchers: Int,
    val login: String,
    val avatarUrl: String,
    val branchesUrl: String
)
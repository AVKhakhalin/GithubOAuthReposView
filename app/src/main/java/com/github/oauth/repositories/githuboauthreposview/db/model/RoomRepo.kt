package com.github.oauth.repositories.githuboauthreposview.db.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
        entity = RoomUser::class,
        parentColumns = ["id"],
        childColumns = ["observerId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RoomRepo(
    @NonNull
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val userId: String,
    val forksCount: Int,
    val watchers: Int,
    val login: String,
    val avatarUrl: String,
    val branchesUrl: String,
    @NonNull
    @ColumnInfo(index = true)
    val observerId: Int
)
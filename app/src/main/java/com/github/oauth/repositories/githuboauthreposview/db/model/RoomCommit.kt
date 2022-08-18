package com.github.oauth.repositories.githuboauthreposview.db.model

import androidx.annotation.NonNull
import androidx.room.*

@Entity(foreignKeys = [
    ForeignKey(
        entity = RoomRepo::class,
        parentColumns = ["id"],
        childColumns = ["repoId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RoomCommit(
    @NonNull
    @PrimaryKey
    val id: String, // хеш-код
    @NonNull
    @ColumnInfo(index = true)
    val repoId: Int,
    val message: String,
    val authorName: String,
    val date: String
)
package com.github.oauth.repositories.githuboauthreposview.db.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class RoomGithubCommit(
    @NonNull
    @PrimaryKey
    val id: String, // это sha, хеш-код коммита
    val repoName: String,
    val message: String,
    val authorName: String,
    val date: String
)

//@Entity(
//    foreignKeys = [
//        ForeignKey(
//            entity = RoomGithubRepo::class,
//            parentColumns = ["name"],
//            childColumns = ["repoName"],
////            onUpdate = ForeignKey.CASCADE,
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
//)
//@Entity
//data class RoomGithubCommit(
//    @NonNull
//    @PrimaryKey
//    val id: String, // это sha, хеш-код коммита
//    @NonNull
//    @ColumnInfo(index = true)
//    val repoName: String,
//    val message: String,
//    val authorName: String,
//    val date: String
//)


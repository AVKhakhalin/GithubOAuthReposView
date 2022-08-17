package com.github.oauth.repositories.githuboauthreposview.db.model

import androidx.annotation.NonNull
import androidx.room.*

@Entity
data class RoomGithubRepo(
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

//@Entity(
//    indices = [Index("name", unique = true)],
//    foreignKeys = [
//        ForeignKey(
//            entity = RoomGithubUser::class,
//            parentColumns = ["id"],
//            childColumns = ["userId"],
////            onUpdate = ForeignKey.CASCADE,
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
//)
//data class RoomGithubRepo(
//    @NonNull
//    @PrimaryKey
//    val id: String,
//    @NonNull
//    val name: String,
//    val description: String,
//    @NonNull
//    @ColumnInfo(index = true)
//    val userId: String,
//    val forksCount: Int,
//    val watchers: Int,
//    val login: String,
//    val avatarUrl: String,
//    val branchesUrl: String
//)


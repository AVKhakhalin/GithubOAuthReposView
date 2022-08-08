package com.github.oauth.repositories.githuboauthreposview.db.model
//
//import androidx.room.Entity
//import androidx.room.ForeignKey
//import androidx.room.PrimaryKey
//
//@Entity(
//    foreignKeys = [
//        ForeignKey(
//            entity = RoomGithubRepo::class,
//            parentColumns = ["name"],
//            childColumns = ["repoName"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
//)
//data class RoomGithubCommit(
//    @PrimaryKey val id: String, // это sha, хеш-код коммита
//    val repoName: String,
//    val message: String,
//    val authorName: String,
//    val date: String
//)
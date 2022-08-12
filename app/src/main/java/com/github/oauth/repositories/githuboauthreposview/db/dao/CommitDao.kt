package com.github.oauth.repositories.githuboauthreposview.db.dao

import androidx.room.*

import com.github.oauth.repositories.githuboauthreposview.db.model.RoomGithubCommit
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface CommitDao {

    @Update
    fun insert(commit: RoomGithubCommit): Completable

    @Update
    fun insert(commits: List<RoomGithubCommit>): Completable

    @Query("SELECT * FROM RoomGithubCommit")
    fun getAll(): Single<List<RoomGithubCommit>>

    @Query("SELECT * FROM RoomGithubCommit WHERE repoName = :repoName")
    fun getByRepoId(repoName: String): Single<List<RoomGithubCommit>>
}
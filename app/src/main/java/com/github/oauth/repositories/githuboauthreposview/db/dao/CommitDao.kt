package com.github.oauth.repositories.githuboauthreposview.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomGithubCommit
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface CommitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(commit: RoomGithubCommit): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(commits: List<RoomGithubCommit>): Completable

    @Query("SELECT * FROM RoomGithubCommit")
    fun getAll(): Single<List<RoomGithubCommit>>

    @Query("SELECT * FROM RoomGithubCommit WHERE repoId = :repoId")
    fun getByRepoId(repoId: String): Single<List<RoomGithubCommit>>
}
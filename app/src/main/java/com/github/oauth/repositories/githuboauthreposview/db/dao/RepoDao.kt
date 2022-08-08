package com.github.oauth.repositories.githuboauthreposview.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomGithubRepo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repo: RoomGithubRepo): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repos: List<RoomGithubRepo>): Completable

    @Query("SELECT * FROM RoomGithubRepo")
    fun getAll(): Single<List<RoomGithubRepo>>

    @Query("SELECT * FROM RoomGithubRepo WHERE id = :repoId")
    fun getById(repoId: String): Single<List<RoomGithubRepo>>

    @Query("SELECT * FROM RoomGithubRepo WHERE name = :repoName")
    fun getByName(repoName: String): Single<List<RoomGithubRepo>>

    @Query("SELECT * FROM RoomGithubRepo WHERE userId = :userId")
    fun getByUserId(userId: String): Single<List<RoomGithubRepo>>
}
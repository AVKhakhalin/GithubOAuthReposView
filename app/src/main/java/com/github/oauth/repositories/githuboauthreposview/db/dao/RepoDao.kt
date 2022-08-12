package com.github.oauth.repositories.githuboauthreposview.db.dao

import androidx.room.*
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomGithubRepo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface RepoDao {

    @Update
    fun insert(repo: RoomGithubRepo): Completable

    @Update
    fun insert(repos: List<RoomGithubRepo>): Completable

    @Query("SELECT * FROM RoomGithubRepo")
    fun getAll(): Single<List<RoomGithubRepo>>

    @Query("SELECT * FROM RoomGithubRepo WHERE id = :repoId")
    fun getById(repoId: String): Single<List<RoomGithubRepo>>

    @Query("SELECT * FROM RoomGithubRepo WHERE name = :repoName")
    fun getByName(repoName: String): Single<List<RoomGithubRepo>>

    @Query("SELECT * FROM RoomGithubRepo WHERE login = :userLogin")
    fun getByUserLogin(userLogin: String): Single<List<RoomGithubRepo>>
}
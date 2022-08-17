package com.github.oauth.repositories.githuboauthreposview.db.dao

import androidx.room.*
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomGithubRepo
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomRepo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface RoomRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repo: RoomGithubRepo): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repo: List<RoomGithubRepo>): Completable

    @Update
    fun update(repo: RoomGithubRepo): Completable

    @Query("SELECT * FROM RoomGithubRepo")
    fun getAll(): Single<List<RoomGithubRepo>>

    @Delete
    fun delete(repo: RoomGithubRepo): Completable

    @Query("SELECT * FROM RoomGithubRepo WHERE name = :name LIMIT 1")
    fun getByLogin(name: String): Single<RoomGithubRepo>
}
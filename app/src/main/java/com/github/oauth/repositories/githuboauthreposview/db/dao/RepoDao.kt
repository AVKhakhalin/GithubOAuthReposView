package com.github.oauth.repositories.githuboauthreposview.db.dao

import androidx.room.*
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomRepo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repo: RoomRepo): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repos: List<RoomRepo>): Completable

    @Query("SELECT * FROM RoomRepo")
    fun getAll(): Single<List<RoomRepo>>

    @Query("DELETE FROM RoomRepo WHERE login = :userLogin")
    fun deleteByUserLogin(userLogin: String): Completable

    @Query("SELECT * FROM RoomRepo WHERE id = :repoId")
    fun getById(repoId: String): Single<List<RoomRepo>>

    @Query("SELECT * FROM RoomRepo WHERE name = :repoName")
    fun getByName(repoName: String): Single<List<RoomRepo>>

    @Query("SELECT * FROM RoomRepo WHERE login = :userLogin")
    fun getByUserLogin(userLogin: String): Single<List<RoomRepo>>
}
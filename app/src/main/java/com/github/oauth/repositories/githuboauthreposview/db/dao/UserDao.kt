package com.github.oauth.repositories.githuboauthreposview.db.dao

import androidx.room.*
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomGithubUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: RoomGithubUser): Completable

    @Update
    fun update(user: RoomGithubUser): Completable

    @Delete
    fun delete(user: RoomGithubUser): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<RoomGithubUser>): Completable

    @Query("SELECT * FROM RoomGithubUser")
    fun getAll(): Single<List<RoomGithubUser>>

    @Query("SELECT * FROM RoomGithubUser WHERE login = :login LIMIT 1")
    fun getByLogin(login: String): Maybe<RoomGithubUser>
}
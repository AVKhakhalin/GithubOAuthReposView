package com.github.oauth.repositories.githuboauthreposview.db.dao

import androidx.room.*
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: RoomUser): Completable

    @Update
    fun update(user: RoomUser): Completable

    @Query("SELECT * FROM RoomUser")
    fun getAll(): Single<List<RoomUser>>

    @Delete
    fun delete(user: RoomUser): Completable

    @Query("SELECT * FROM RoomUser WHERE login = :userLogin LIMIT 1")
    fun getByLogin(userLogin: String): Single<RoomUser>
}
package com.github.oauth.repositories.githuboauthreposview.db.dao

import androidx.room.*
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomCommit
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(commit: RoomCommit): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(commit: List<RoomCommit>): Completable

    @Update
    fun update(commit: RoomCommit): Completable

    @Query("SELECT * FROM RoomCommit")
    fun getAll(): Single<List<RoomCommit>>

    @Delete
    fun delete(commit: RoomCommit): Completable

    @Query("SELECT * FROM RoomCommit WHERE repoId = :repoId")
    fun getByRepoId(repoId: Int): Single<List<RoomCommit>>
}
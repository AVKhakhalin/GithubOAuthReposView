package com.github.oauth.repositories.githuboauthreposview.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.oauth.repositories.githuboauthreposview.db.dao.CommitDao
import com.github.oauth.repositories.githuboauthreposview.db.dao.RepoDao
import com.github.oauth.repositories.githuboauthreposview.db.dao.UserDao
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomCommit
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomRepo
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomUser
import com.github.oauth.repositories.githuboauthreposview.utils.DATABASE_NAME

@Database(
    entities = [
        RoomUser::class,
        RoomRepo::class,
        RoomCommit::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract val userDao: UserDao
    abstract val repoDao: RepoDao
    abstract val roomCommitDao: CommitDao

    companion object {
        @Volatile // All threads have immediate access to this property
        private var instance: AppDatabase? = null

        private val LOCK = Any() // Makes sure no threads making the same thing at the same time

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance?: buildDatabase(context).also { instance = it }
        }
    }
}
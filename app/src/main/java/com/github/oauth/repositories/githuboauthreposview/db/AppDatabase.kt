package com.github.oauth.repositories.githuboauthreposview.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.oauth.repositories.githuboauthreposview.db.dao.*
import com.github.oauth.repositories.githuboauthreposview.db.model.*
import com.github.oauth.repositories.githuboauthreposview.utils.DATABASE_NAME

@Database(
    entities = [
        RoomGithubUser::class,
        RoomGithubRepo::class,
        RoomGithubCommit::class,
        RoomRepo::class,
        RoomCommit::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract val userDao: UserDao

    abstract val repositoryDao: RepoDao

    abstract val commitDao: CommitDao

    abstract val repoDao: RoomRepoDao

    abstract val roomCommitDao: RoomCommitDao

    companion object {
//        val instance by lazy {
//            Room.databaseBuilder(App.instance, AppDatabase::class.java, DATABASE_NAME)
//                .fallbackToDestructiveMigration()
//                .build()
//        }
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
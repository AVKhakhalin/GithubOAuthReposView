package com.github.oauth.repositories.githuboauthreposview.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.oauth.repositories.githuboauthreposview.app.App
import com.github.oauth.repositories.githuboauthreposview.db.dao.RepoDao
import com.github.oauth.repositories.githuboauthreposview.db.dao.UserDao
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomGithubRepo
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomGithubUser
import com.github.oauth.repositories.githuboauthreposview.utils.DATABASE_NAME

@Database(
    entities = [
        RoomGithubUser::class,
        RoomGithubRepo::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract val userDao: UserDao

    abstract val repositoryDao: RepoDao

    companion object {
        val instance by lazy {
            Room.databaseBuilder(App.instance, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}
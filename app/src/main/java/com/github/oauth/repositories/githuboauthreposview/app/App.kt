package com.github.oauth.repositories.githuboauthreposview.app

import android.app.Application
import com.github.oauth.repositories.githuboauthreposview.di.components.AppComponent
import com.github.oauth.repositories.githuboauthreposview.di.components.DaggerAppComponent
import com.github.oauth.repositories.githuboauthreposview.di.components.GithubReposSubcomponent
import com.github.oauth.repositories.githuboauthreposview.di.components.GithubUsersSubcomponent
import com.github.oauth.repositories.githuboauthreposview.di.modules.AppModule
import com.github.oauth.repositories.githuboauthreposview.di.scope.containers.ReposScopeContainer
import com.github.oauth.repositories.githuboauthreposview.di.scope.containers.UsersScopeContainer

class App: Application(), UsersScopeContainer, ReposScopeContainer {
    /** Исходные данные */ //region
    // appComponent
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
    // usersSubcomponent
    var usersSubcomponent: GithubUsersSubcomponent? = null
    // reposSubcomponent
    var reposSubcomponent: GithubReposSubcomponent? = null
    //endregion

    override fun onCreate() {
        super.onCreate()
        _instance = this
    }

    companion object {
        private var _instance: App? = null
        val instance
            get() = _instance!!
    }

    /** Методы UsersScopeContainer */ //region
    override fun initGithubUsersSubcomponent() = appComponent.usersSubcomponent().also {
        usersSubcomponent = it
    }
    override fun destroyGithubUsersSubcomponent() {
        usersSubcomponent = null
    }
    //endregion

    /** Методы ReposScopeContainer */ //region
    override fun initGithubReposSubcomponent() = appComponent.reposSubcomponent().also {
        reposSubcomponent = it
    }

    override fun destroyGithubReposSubcomponent() {
        reposSubcomponent = null
    }
    //endregion
}
package com.github.oauth.repositories.githuboauthreposview.app

import android.app.Application
import com.github.oauth.repositories.githuboauthreposview.di.components.AppComponent
import com.github.oauth.repositories.githuboauthreposview.di.components.DaggerAppComponent
import com.github.oauth.repositories.githuboauthreposview.di.components.GithubUsersSubcomponent
import com.github.oauth.repositories.githuboauthreposview.di.modules.AppModule
import com.github.oauth.repositories.githuboauthreposview.di.scope.containers.UsersScopeContainer

class App: Application(), UsersScopeContainer {
    /** Исходные данные */ //region
    // appComponent
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this)) // TODO: Разобраться в необходимости
            .build()
    }

    // usersSubcomponent
    var usersSubcomponent: GithubUsersSubcomponent? = null

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
}
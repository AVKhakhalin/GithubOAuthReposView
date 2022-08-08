package com.github.oauth.repositories.githuboauthreposview.app

import android.app.Application
import com.github.oauth.repositories.githuboauthreposview.di.components.AppComponent
import com.github.oauth.repositories.githuboauthreposview.di.components.DaggerAppComponent
import com.github.oauth.repositories.githuboauthreposview.di.modules.AppModule

class App: Application() {
    /** Исходные данные */ //region
    // appComponent
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this)) // TODO: Разобраться в необходимости
            .build()
    }
    override fun onCreate() {
        super.onCreate()
        _instance = this
    }

    companion object {
        private var _instance: App? = null
        val instance
            get() = _instance!!
    }
}
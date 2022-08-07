package com.github.oauth.repositories.githuboauthreposview.app

import android.app.Application

class App: Application() {
    /** Исходные данные */ //region
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
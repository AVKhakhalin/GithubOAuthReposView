package com.github.oauth.repositories.githuboauthreposview.di.modules

import android.content.Context
import com.github.oauth.repositories.githuboauthreposview.app.App
import com.github.oauth.repositories.githuboauthreposview.utils.resources.ResourcesProvider
import com.github.oauth.repositories.githuboauthreposview.utils.resources.ResourcesProviderImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Singleton
    @Provides
    fun context(): Context {
        return app
    }

    @Singleton
    @Provides
    fun app(): App {
        return app
    }

    @Singleton
    @Provides
    fun resProvider(resourcesProviderImpl: ResourcesProviderImpl): ResourcesProvider {
        return resourcesProviderImpl
    }
}
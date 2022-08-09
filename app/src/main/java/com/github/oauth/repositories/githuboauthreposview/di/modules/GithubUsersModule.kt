package com.github.oauth.repositories.githuboauthreposview.di.modules

import com.github.oauth.repositories.githuboauthreposview.app.App
import com.github.oauth.repositories.githuboauthreposview.di.scope.UsersScope
import com.github.oauth.repositories.githuboauthreposview.di.scope.containers.UsersScopeContainer
import dagger.Module
import dagger.Provides

@Module
abstract class GithubUsersModule {

    companion object {
        @UsersScope
        @Provides
        fun scopeContainer(app: App): UsersScopeContainer = app
    }
}
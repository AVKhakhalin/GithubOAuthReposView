package com.github.oauth.repositories.githuboauthreposview.di.modules

import com.github.oauth.repositories.githuboauthreposview.app.App
import com.github.oauth.repositories.githuboauthreposview.di.scope.ForksScope
import com.github.oauth.repositories.githuboauthreposview.di.scope.containers.ForksScopeContainer
import dagger.Module
import dagger.Provides

@Module
abstract class GithubForksModule {

    companion object {
        @ForksScope
        @Provides
        fun forksScopeContainer(app: App): ForksScopeContainer = app
    }
}
package com.github.oauth.repositories.githuboauthreposview.di.components

import com.github.oauth.repositories.githuboauthreposview.di.modules.GithubUsersModule
import com.github.oauth.repositories.githuboauthreposview.di.scope.UsersScope
import com.github.oauth.repositories.githuboauthreposview.view.users.UsersPresenter
import dagger.Subcomponent

@UsersScope
@Subcomponent(
    modules = [
        GithubUsersModule::class
    ]
)
interface GithubUsersSubcomponent {

    fun provideUsersPresenter(): UsersPresenter
}
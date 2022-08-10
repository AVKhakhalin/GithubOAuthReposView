package com.github.oauth.repositories.githuboauthreposview.di.components

import com.github.oauth.repositories.githuboauthreposview.di.modules.GithubReposModule
import com.github.oauth.repositories.githuboauthreposview.di.scope.ReposScope
import com.github.oauth.repositories.githuboauthreposview.view.repos.ReposPresenter
import dagger.Subcomponent

@ReposScope
@Subcomponent(
    modules = [
        GithubReposModule::class
    ]
)

interface GithubReposSubcomponent {

    fun provideReposPresenter(): ReposPresenter
}
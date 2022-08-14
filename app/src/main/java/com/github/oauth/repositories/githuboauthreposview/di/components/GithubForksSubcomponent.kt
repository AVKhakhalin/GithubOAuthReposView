package com.github.oauth.repositories.githuboauthreposview.di.components

import com.github.oauth.repositories.githuboauthreposview.di.modules.GithubForksModule
import com.github.oauth.repositories.githuboauthreposview.di.scope.ForksScope
import com.github.oauth.repositories.githuboauthreposview.view.forks.ForksPresenter
import dagger.Subcomponent

@ForksScope
@Subcomponent(
    modules = [
        GithubForksModule::class
    ]
)

interface GithubForksSubcomponent {

    fun provideForksPresenter(): ForksPresenter
}
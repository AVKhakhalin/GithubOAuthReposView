package com.github.oauth.repositories.githuboauthreposview.di.scope.containers

import com.github.oauth.repositories.githuboauthreposview.di.components.GithubForksSubcomponent


interface ForksScopeContainer {

    fun initForksSubcomponent(): GithubForksSubcomponent

    fun destroyForksSubcomponent()
}
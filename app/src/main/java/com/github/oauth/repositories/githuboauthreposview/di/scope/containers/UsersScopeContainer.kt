package com.github.oauth.repositories.githuboauthreposview.di.scope.containers

import com.github.oauth.repositories.githuboauthreposview.di.components.GithubUsersSubcomponent

interface UsersScopeContainer {

    fun initGithubUsersSubcomponent(): GithubUsersSubcomponent

    fun destroyGithubUsersSubcomponent()
}
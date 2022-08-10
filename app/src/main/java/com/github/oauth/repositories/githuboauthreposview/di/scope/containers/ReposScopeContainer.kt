package com.github.oauth.repositories.githuboauthreposview.di.scope.containers

import com.github.oauth.repositories.githuboauthreposview.di.components.GithubReposSubcomponent


interface ReposScopeContainer {

    fun initGithubReposSubcomponent(): GithubReposSubcomponent

    fun destroyGithubReposSubcomponent()
}
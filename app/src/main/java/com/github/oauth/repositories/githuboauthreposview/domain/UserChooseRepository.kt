package com.github.oauth.repositories.githuboauthreposview.domain

import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel

interface UserChooseRepository {
    fun setGithubUserModel(githubUserModel: GithubUserModel)
    fun getGithubUserModel(): GithubUserModel
    fun setGithubRepoModel(githubRepoModel: GithubRepoModel)
    fun setGithubReposModel(repos: List<GithubRepoModel>)
    fun getGithubRepoModel(): GithubRepoModel
}
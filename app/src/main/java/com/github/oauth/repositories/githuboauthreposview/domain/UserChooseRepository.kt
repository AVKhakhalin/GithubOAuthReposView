package com.github.oauth.repositories.githuboauthreposview.domain

import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel

interface UserChooseRepository {
    // Данные о пользователе
    fun setGithubUserModel(githubUserModel: GithubUserModel)
    fun getGithubUserModel(): GithubUserModel
    // Данные о репозиториях пользователя
    fun setGithubRepoModel(githubRepoModel: GithubRepoModel)
    fun setGithubReposModel(repos: List<GithubRepoModel>)
    fun getGithubRepoModel(): GithubRepoModel
    // Данные о коммитах репозитория
    fun setGithubCommitModel(githubCommitModel: GithubCommitModel)
    fun setGithubCommitsModel(repos: List<GithubCommitModel>)
    fun getGithubCommitModel(): GithubCommitModel
    // Данные о количестве запросов на github.com
    fun setNumberLimitRequest(numberLimitRequests: Int)
    fun getNumberLimitRequest(): Int
    fun setRemainingRequest(numberRemainingRequests: Int)
    fun getRemainingRequest(): Int
    // Данные о наличии связи сервером

}
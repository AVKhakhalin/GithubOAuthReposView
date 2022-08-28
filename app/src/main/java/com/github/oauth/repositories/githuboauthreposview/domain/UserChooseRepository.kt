package com.github.oauth.repositories.githuboauthreposview.domain

import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import com.github.oauth.repositories.githuboauthreposview.utils.ServerResponseStatusCode

interface UserChooseRepository {
    // Данные о пользователе
    fun setGithubUserModel(githubUserModel: GithubUserModel)
    fun getGithubUserModel(): GithubUserModel
    fun getUserId(): Int
    // Данные о токене
    fun setToken(token: String)
    fun getToken(): String
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
    fun setNumberRemainingRequest(numberRemainingRequests: Int)
    fun getNumberRemainingRequest(): Int
    fun setResponseCode(responseCode: ServerResponseStatusCode)
    fun getResponseCode(): ServerResponseStatusCode
    fun setRequestTime(requestTime: Long)
    fun getActualRequestsTimesList(): List<Long>
    fun getWaitingTime(): Pair<String, String>
    fun getWaitingMinutes(): Pair<Long, Long>
    // Информация об обновлении имеющихся данных с сервера github.com
    fun getIsAvatarUpdated(): Boolean
    fun setIsAvatarUpdated(isAvatarUpdated: Boolean)
    fun getIsUserModelUpdated(): Boolean
    fun setIsUserModelUpdated(isUserModelUpdated: Boolean)
    fun getIsRepoModelListUpdated(): Boolean
    fun setIsRepoModelListUpdated(isRepoModelListUpdated: Boolean)
    fun getIsCommitModelsUpdated(repoId: Int): Boolean
    fun setIsCommitModelsUpdated(repoId: Int)
}
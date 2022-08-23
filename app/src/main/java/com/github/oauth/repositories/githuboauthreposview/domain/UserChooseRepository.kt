package com.github.oauth.repositories.githuboauthreposview.domain

import android.widget.Toast
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import com.github.oauth.repositories.githuboauthreposview.remote.BaseInterceptor
import com.github.oauth.repositories.githuboauthreposview.utils.ServerResponseStatusCode
import java.util.*
import java.util.concurrent.TimeUnit

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
    fun setNumberRemainingRequest(numberRemainingRequests: Int)
    fun getNumberRemainingRequest(): Int
    fun setResponseCode(responseCode: ServerResponseStatusCode)
    fun getResponseCode(): ServerResponseStatusCode
    fun setRequestTime(requestTime: Long)
    fun getActualRequestsTimesList(): List<Long>
    fun getWaitingTime(): Pair<String, String>
    fun getWaitingMinutes(): Pair<Long, Long>
}
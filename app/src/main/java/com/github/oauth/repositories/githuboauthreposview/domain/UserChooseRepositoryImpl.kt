package com.github.oauth.repositories.githuboauthreposview.domain

import com.github.oauth.repositories.githuboauthreposview.model.*
import com.github.oauth.repositories.githuboauthreposview.utils.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.TimeUnit

class UserChooseRepositoryImpl: UserChooseRepository {
    /** Исходные данные */ //region
    // githubUserModel
    private var githubUserModel: GithubUserModel =
        GithubUserModel("", "", "", "")
    // token
    private var token: String = ""
    // githubRepoModel
    private var githubRepoModel: GithubRepoModel =
        GithubRepoModel(-1, "", "",
            GithubRepoOwnerModel("", "", ""),
            "", 0, 0)
    private var repos: List<GithubRepoModel> = listOf()
    // githubCommitModel
    private var githubCommitModel: GithubCommitModel =
        GithubCommitModel("", GithubCommitCommitInfoModel("",
            RoomGithubCommitInfoAuthorModel("", "")
        ))
    private var commits: List<GithubCommitModel> = listOf()
    // Количество запросов (разрешённых и оставшихся)
    private var numberLimitRequests: Int = DEFAULT_NUMBER_GITHUB_LIMIT_REQUEST
    private var numberRemainingRequests: Int = -1
    private var responseCode: ServerResponseStatusCode = ServerResponseStatusCode.SUCCESS
    private var requestsTimesList: CopyOnWriteArrayList<Long> = CopyOnWriteArrayList<Long>()
    // Информация об обновлении имеющихся данных с сервера github.com
    private var isAvatarUpdated: Boolean = false
    private var isUserModelUpdated: Boolean = false
    private var isRepoModelListUpdated: Boolean = false
    private var isCommitModelsUpdated: CopyOnWriteArrayList<CommitModelUpdated> =
        CopyOnWriteArrayList<CommitModelUpdated>()
    //endregion

    //region Методы для работы с пользователем
    override fun setGithubUserModel(githubUserModel: GithubUserModel) {
        this.githubUserModel = githubUserModel
    }
    override fun getGithubUserModel(): GithubUserModel = githubUserModel
    //endregion

    //region Методы работы с токеном
    override fun setToken(token: String) {
        this.token = token
    }
    override fun getToken(): String {
        return token
    }
    //endregion

    //region Методы для работы с репозиториями
    override fun setGithubRepoModel(githubRepoModel: GithubRepoModel) {
        this.githubRepoModel = githubRepoModel
    }
    override fun setGithubReposModel(repos: List<GithubRepoModel>) {
        this.repos = repos
    }
    override fun getGithubRepoModel(): GithubRepoModel = githubRepoModel
    //endregion

    //region Методы для работы с коммитами
    override fun setGithubCommitModel(githubCommitModel: GithubCommitModel) {
        this.githubCommitModel = githubCommitModel
    }
    override fun setGithubCommitsModel(commits: List<GithubCommitModel>) {
        this.commits = commits
    }
    override fun getGithubCommitModel(): GithubCommitModel = githubCommitModel
    //endregion

    //region Методы для работы с количеством запросов на github.com
    override fun setNumberLimitRequest(numberLimitRequests: Int) {
        this.numberLimitRequests = numberLimitRequests
    }
    override fun getNumberLimitRequest(): Int = numberLimitRequests
    override fun setNumberRemainingRequest(numberRemainingRequests: Int) {
        this.numberRemainingRequests = numberRemainingRequests
    }
    override fun getNumberRemainingRequest(): Int = numberRemainingRequests
    override fun setResponseCode(responseCode: ServerResponseStatusCode) {
        this.responseCode = responseCode
    }
    override fun getResponseCode(): ServerResponseStatusCode = responseCode
    override fun setRequestTime(requestTime: Long) {
        requestsTimesList.add(requestTime)
        if (requestsTimesList.size > numberLimitRequests) {
            getActualRequestsTimesList().forEachIndexed { index, it ->
                if (index == 0) requestsTimesList.clear()
                requestsTimesList.add(it)
            }
        }
    }
    override fun getActualRequestsTimesList(): List<Long> {
        val result = requestsTimesList.takeLastWhile { it > Date().time - MILLISECONDS_IN_HOUR }
        return result
    }

    override fun getWaitingTime(): Pair<String, String> {
        val minutesAndMilliseconds: Pair<Long, Long> = getWaitingMinutes()
        val futureTime: String = SimpleDateFormat(FORMAT_FUTURE_TIME,
            Locale(LOCALE_LANGUAGE_DATE_FORMAT)).
        format(Date().time + minutesAndMilliseconds.second)
        return Pair(setCorrectMinutes(minutesAndMilliseconds.first), futureTime)
    }

    override fun getWaitingMinutes(): Pair<Long, Long> {
        var waitingMilliseconds: Long = 0L
        var waitingMinutes: Long = 0L
        return if (requestsTimesList.size == 0) {
            Pair(0L, 0L)
        } else {
            if (requestsTimesList[0] < requestsTimesList[requestsTimesList.size - 1]) {
                waitingMilliseconds = MILLISECONDS_IN_HOUR - (Date().time - requestsTimesList[0])
                waitingMinutes = TimeUnit.MINUTES.convert(
                    waitingMilliseconds, TimeUnit.MILLISECONDS)

            } else {
                waitingMilliseconds = MILLISECONDS_IN_HOUR -
                        (Date().time - requestsTimesList[requestsTimesList.size - 1])
                waitingMinutes = TimeUnit.MINUTES.convert(
                    waitingMilliseconds, TimeUnit.MILLISECONDS)
            }
            Pair(if (waitingMinutes >= 0) waitingMinutes else 0,
                if (waitingMilliseconds >= 0) waitingMilliseconds else 0)
        }
    }
    //endregion

    //region Информация об обновлении имеющихся данных с сервера github.com
    override fun getIsAvatarUpdated(): Boolean = isAvatarUpdated
    override fun setIsAvatarUpdated(isAvatarUpdated: Boolean) {
        this.isAvatarUpdated = isAvatarUpdated
    }
    override fun getIsUserModelUpdated(): Boolean = isUserModelUpdated
    override fun setIsUserModelUpdated(isUserModelUpdated: Boolean) {
        this.isUserModelUpdated = isUserModelUpdated
    }
    override fun getIsRepoModelListUpdated(): Boolean = isRepoModelListUpdated
    override fun setIsRepoModelListUpdated(isRepoModelListUpdated: Boolean) {
        this.isRepoModelListUpdated = isRepoModelListUpdated
    }
    override fun getIsCommitModelsUpdated(repoId: Int): Boolean {
        var result: Boolean = false
        if (isCommitModelsUpdated.size == 0) {
            repos.forEach {
                isCommitModelsUpdated.
                    add(CommitModelUpdated(it.id, false))
            }
        } else {
            for(index in 0 until isCommitModelsUpdated.size) {
                if (isCommitModelsUpdated[index].repoId == repoId) {
                    result = isCommitModelsUpdated[index].isCommitModelUpdated
                    break
                }
            }
        }
        return result
    }
    override fun setIsCommitModelsUpdated(repoId: Int) {
        if (isCommitModelsUpdated.size == 0) {
            repos.forEach {
                isCommitModelsUpdated.
                add(CommitModelUpdated(it.id, it.id == repoId))
            }
        } else {
            for(index in 0 until isCommitModelsUpdated.size) {
                if (isCommitModelsUpdated[index].repoId == repoId) {
                    isCommitModelsUpdated[index].isCommitModelUpdated = true
                    break
                }
            }
        }
    }
    //endregion
}
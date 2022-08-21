package com.github.oauth.repositories.githuboauthreposview.domain

import com.github.oauth.repositories.githuboauthreposview.model.*

class UserChooseRepositoryImpl: UserChooseRepository {
    /** Исходные данные */ //region
    // githubUserModel
    private var githubUserModel: GithubUserModel =
        GithubUserModel("", "", "", "")
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
    private var numberLimitRequests: Int = -1
    private var numberRemainingRequests: Int = -1
    private var lastDateRequest: String = ""
    //endregion

    //region Методы для работы с пользователем
    override fun setGithubUserModel(githubUserModel: GithubUserModel) {
        this.githubUserModel = githubUserModel
    }
    override fun getGithubUserModel(): GithubUserModel = githubUserModel
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
    override fun setLastDateRequest(lastDateRequest: String) {
        this.lastDateRequest = lastDateRequest
    }
    override fun getLastDateRequest(): String {
        return lastDateRequest
    }
    //endregion
}
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
    //endregion

    //region Методы для работы с пользователем
    override fun setGithubUserModel(githubUserModel: GithubUserModel) {
        this.githubUserModel = githubUserModel
    }
    override fun getGithubUserModel(): GithubUserModel {
        return githubUserModel
    }
    //endregion

    //region Методы для работы с репозиториями
    override fun setGithubRepoModel(githubRepoModel: GithubRepoModel) {
        this.githubRepoModel = githubRepoModel
    }
    override fun setGithubReposModel(repos: List<GithubRepoModel>) {
        this.repos = repos
    }
    override fun getGithubRepoModel(): GithubRepoModel {
        return githubRepoModel
    }
    //endregion

    //region Методы для работы с коммитами
    override fun setGithubCommitModel(githubCommitModel: GithubCommitModel) {
        this.githubCommitModel = githubCommitModel
    }
    override fun setGithubCommitsModel(commits: List<GithubCommitModel>) {
        this.commits = commits
    }
    override fun getGithubCommitModel(): GithubCommitModel {
        return githubCommitModel
    }
    //endregion
}
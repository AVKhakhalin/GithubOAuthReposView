package com.github.oauth.repositories.githuboauthreposview.domain

import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoOwnerModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel


class UserChooseRepositoryImpl: UserChooseRepository {
    /** Исходные данные */ //region
    // githubUserModel
    private var githubUserModel: GithubUserModel =
        GithubUserModel("", "", "", "")

    // githubRepoModel
    private var githubRepoModel: GithubRepoModel =
        GithubRepoModel("", "", "",
            GithubRepoOwnerModel("", "", ""),
            "", 0, 0)
    private var repos: List<GithubRepoModel> = listOf()
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
}
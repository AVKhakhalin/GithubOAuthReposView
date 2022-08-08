package com.github.oauth.repositories.githuboauthreposview.domain.retrofit

import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import io.reactivex.rxjava3.core.Single

interface GithubCommitRetrofit {
    fun getRetrofitCommit(userModel: GithubUserModel, repoName: String
    ): MutableList<GithubCommitModel>
}
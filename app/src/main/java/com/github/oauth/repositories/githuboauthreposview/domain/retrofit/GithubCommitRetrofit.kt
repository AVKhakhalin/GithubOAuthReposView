package com.github.oauth.repositories.githuboauthreposview.domain.retrofit

import com.github.oauth.repositories.githuboauthreposview.view.forks.ForksView

interface GithubCommitRetrofit {
    fun getRetrofitCommit(userLogin: String, repoName: String, forksView: ForksView)
}
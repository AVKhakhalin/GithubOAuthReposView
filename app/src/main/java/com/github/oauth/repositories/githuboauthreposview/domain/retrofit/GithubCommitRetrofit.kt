package com.github.oauth.repositories.githuboauthreposview.domain.retrofit

import com.github.oauth.repositories.githuboauthreposview.view.forks.ForksView

interface GithubCommitRetrofit {
    fun getRetrofitCommit(repoId: Int, forksView: ForksView)
}
package com.github.oauth.repositories.githuboauthreposview.domain

import com.github.oauth.repositories.githuboauthreposview.domain.cache.GithubCommitCache
import com.github.oauth.repositories.githuboauthreposview.domain.retrofit.GithubCommitRetrofit
import com.github.oauth.repositories.githuboauthreposview.utils.connectivity.NetworkStatus
import com.github.oauth.repositories.githuboauthreposview.view.forks.ForksView

class GithubCommitRepositoryImpl(
    private val networkStatus: NetworkStatus,
    private val githubCommitRetrofit: GithubCommitRetrofit,
    private val githubCommitCache: GithubCommitCache
): GithubCommitRepository {
    override fun getCommits(userLogin: String, repoName: String, forksView: ForksView) {
        if (networkStatus.isOnline())
            githubCommitRetrofit.getRetrofitCommit(userLogin, repoName, forksView)
        else
            githubCommitCache.getCacheCommit(userLogin, repoName, forksView)
    }
}
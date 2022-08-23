package com.github.oauth.repositories.githuboauthreposview.domain

import com.github.oauth.repositories.githuboauthreposview.domain.cache.GithubCommitCache
import com.github.oauth.repositories.githuboauthreposview.domain.retrofit.GithubCommitRetrofit
import com.github.oauth.repositories.githuboauthreposview.utils.connectivity.NetworkStatus
import com.github.oauth.repositories.githuboauthreposview.view.forks.ForksView

class GithubCommitRepositoryImpl(
    private val networkStatus: NetworkStatus,
    private val githubCommitRetrofit: GithubCommitRetrofit,
    private val githubCommitCache: GithubCommitCache,
    private val userChoose: UserChooseRepository
): GithubCommitRepository {
    override fun getCommits(repoId: Int, forksView: ForksView) {
        if ((networkStatus.isOnline()) && (!userChoose.getIsCommitModelsUpdated(repoId)))
            githubCommitRetrofit.getRetrofitCommit(repoId, forksView)
        else
            githubCommitCache.getCacheCommit(repoId, forksView)
    }
}
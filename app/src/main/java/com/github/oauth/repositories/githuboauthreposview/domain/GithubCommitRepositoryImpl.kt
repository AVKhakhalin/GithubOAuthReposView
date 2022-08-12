package com.github.oauth.repositories.githuboauthreposview.domain

import com.github.oauth.repositories.githuboauthreposview.domain.cache.GithubCommitCache
import com.github.oauth.repositories.githuboauthreposview.domain.retrofit.GithubCommitRetrofit
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import com.github.oauth.repositories.githuboauthreposview.utils.connectivity.NetworkStatus
import io.reactivex.rxjava3.core.Single

class GithubCommitRepositoryImpl(
    private val networkStatus: NetworkStatus,
    private val githubCommitRetrofit: GithubCommitRetrofit,
    private val githubCommitCache: GithubCommitCache
): GithubCommitRepository {
    override fun getCommits(userLogin: String, repoName: String): Single<List<GithubCommitModel>> {
        return if (networkStatus.isOnline())
            githubCommitRetrofit.getRetrofitCommit(userLogin, repoName)
        else
            githubCommitCache.getCacheCommit(userLogin)
    }
}
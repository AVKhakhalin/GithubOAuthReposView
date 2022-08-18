package com.github.oauth.repositories.githuboauthreposview.domain

import com.github.oauth.repositories.githuboauthreposview.domain.cache.GithubRepoCache
import com.github.oauth.repositories.githuboauthreposview.domain.retrofit.GithubRepoRetrofit
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.utils.connectivity.NetworkStatus
import io.reactivex.rxjava3.core.Single

class GithubRepoRepositoryImpl(
    private val networkStatus: NetworkStatus,
    private val githubRepoRetrofit: GithubRepoRetrofit,
    private val githubRepoCache: GithubRepoCache
): GithubRepoRepository {
    override fun getRepos(userLogin: String): Single<List<GithubRepoModel>> {
        return if (networkStatus.isOnline()) {
            githubRepoRetrofit.getRetrofitRepo(userLogin)
        } else {
            githubRepoCache.getCacheRepo(userLogin)
        }
    }
}
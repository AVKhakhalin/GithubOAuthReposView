package com.github.oauth.repositories.githuboauthreposview.domain

import com.github.oauth.repositories.githuboauthreposview.domain.cache.GithubRepoCache
import com.github.oauth.repositories.githuboauthreposview.domain.retrofit.GithubRepoRetrofit
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import com.github.oauth.repositories.githuboauthreposview.utils.connectivity.NetworkStatus
import io.reactivex.rxjava3.core.Single

class GithubRepoRepositoryImpl(
    private val networkStatus: NetworkStatus,
    private val githubRepoRetrofit: GithubRepoRetrofit,
    private val githubRepoCache: GithubRepoCache
): GithubRepoRepository {
    override fun getRepos(userModel: GithubUserModel): Single<List<GithubRepoModel>> {
        return if (networkStatus.isOnline())
            githubRepoRetrofit.getRetrofitRepo(userModel)
        else
            githubRepoCache.getCacheRepo(userModel)
    }
}
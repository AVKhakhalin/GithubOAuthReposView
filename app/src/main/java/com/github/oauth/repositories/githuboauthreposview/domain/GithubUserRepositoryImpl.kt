package com.github.oauth.repositories.githuboauthreposview.domain

import com.github.oauth.repositories.githuboauthreposview.domain.cache.GithubUserCache
import com.github.oauth.repositories.githuboauthreposview.domain.retrofit.GithubUserRetrofit
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import com.github.oauth.repositories.githuboauthreposview.utils.connectivity.NetworkStatus
import io.reactivex.rxjava3.core.Single

class GithubUserRepositoryImpl(
    private val networkStatus: NetworkStatus,
    private val githubUserRetrofit: GithubUserRetrofit,
    private val githubUserCache: GithubUserCache
): GithubUserRepository {
    override fun getUser(login: String): Single<GithubUserModel> {
        return if (networkStatus.isOnline())
            githubUserRetrofit.getRetrofitUser(login)
        else
            githubUserCache.getCacheUser(login)
    }
}
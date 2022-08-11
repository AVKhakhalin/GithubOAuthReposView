package com.github.oauth.repositories.githuboauthreposview.domain.cache

import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import io.reactivex.rxjava3.core.Single

interface GithubRepoCache {
    fun getCacheRepo(userLogin: String): Single<List<GithubRepoModel>>
}
package com.github.oauth.repositories.githuboauthreposview.domain.cache

import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import io.reactivex.rxjava3.core.Maybe

interface GithubUserCache {
    fun getCacheUser(login: String): Maybe<GithubUserModel>
}
package com.github.oauth.repositories.githuboauthreposview.domain

import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import io.reactivex.rxjava3.core.Single

interface GithubUserRepository {
    fun getUser(login: String): Single<GithubUserModel>
    fun getToken(login: String): Single<String>
}
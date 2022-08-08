package com.github.oauth.repositories.githuboauthreposview.domain

import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import io.reactivex.rxjava3.core.Maybe

interface GithubUserRepository {
    fun getUser(login: String): Maybe<GithubUserModel>
}
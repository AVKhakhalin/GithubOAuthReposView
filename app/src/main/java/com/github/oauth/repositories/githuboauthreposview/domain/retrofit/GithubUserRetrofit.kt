package com.github.oauth.repositories.githuboauthreposview.domain.retrofit

import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import io.reactivex.rxjava3.core.Maybe

interface GithubUserRetrofit {
    fun getRetrofitUser(login: String): Maybe<GithubUserModel>
}
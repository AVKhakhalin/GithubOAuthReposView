package com.github.oauth.repositories.githuboauthreposview.domain.retrofit

import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import io.reactivex.rxjava3.core.Single

interface GithubUserRetrofit {
    fun getRetrofitUser(token: String, userLogin: String): Single<GithubUserModel>
    fun getRetrofitToken(userLogin: String): Single<String>
}
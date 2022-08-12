package com.github.oauth.repositories.githuboauthreposview.domain.retrofit

import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import io.reactivex.rxjava3.core.Single

interface GithubRepoRetrofit {
    fun getRetrofitRepo(userLogin: String): Single<List<GithubRepoModel>>
}
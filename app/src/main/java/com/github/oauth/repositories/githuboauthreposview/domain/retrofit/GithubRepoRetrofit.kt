package com.github.oauth.repositories.githuboauthreposview.domain.retrofit

import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import io.reactivex.rxjava3.core.Single

interface GithubRepoRetrofit {
    fun getRetrofitRepo(userModel: GithubUserModel): Single<List<GithubRepoModel>>
}
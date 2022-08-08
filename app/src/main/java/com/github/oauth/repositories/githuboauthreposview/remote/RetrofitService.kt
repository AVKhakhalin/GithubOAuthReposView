package com.github.oauth.repositories.githuboauthreposview.remote

import com.github.oauth.repositories.githuboauthreposview.model.GithubBrancheModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitService {

    @GET
    fun getUser(login: String): Maybe<GithubUserModel>

    @GET
    fun getRepos(@Url url: String): Single<List<GithubRepoModel>>

    @GET
    fun getBranches(@Url url: String): Single<List<GithubBrancheModel>>

    @GET
    fun getCommits(@Url url: String): Single<List<GithubCommitModel>>
}
package com.github.oauth.repositories.githuboauthreposview.remote

import com.github.oauth.repositories.githuboauthreposview.model.GithubBrancheModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface RetrofitService {

    @GET("/users/{user_login}")
    fun getUser(@Path("user_login") userLogin: String): Single<GithubUserModel>

    @GET
    fun getRepos(@Url url: String): Single<List<GithubRepoModel>>

    @GET
    fun getBranches(@Url url: String): Single<List<GithubBrancheModel>>

    @GET
    fun getCommits(@Url url: String): Single<List<GithubCommitModel>>
}
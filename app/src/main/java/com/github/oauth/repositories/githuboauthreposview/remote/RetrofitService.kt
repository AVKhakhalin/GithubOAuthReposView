package com.github.oauth.repositories.githuboauthreposview.remote

import com.github.oauth.repositories.githuboauthreposview.model.GithubBrancheModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import com.github.oauth.repositories.githuboauthreposview.utils.NUMBER_RESULTS_ON_PAGE
import com.github.oauth.repositories.githuboauthreposview.utils.PER_PAGE_NAME
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface RetrofitService {

    // TOKEN -------------------------------------------------
    @Headers("Accept: application/vnd.github+json")
    @GET
    fun getToken(@Url url: String): Single<String>

    // USERS -------------------------------------------------
    @Headers("Accept: application/vnd.github+json")
    @GET("/users/{user_login}")
    fun getUser(@Header("access_token") accessToken: String,
                @Path("user_login") userLogin: String
    ): Single<GithubUserModel>

    // REPOS -------------------------------------------------
    @Headers("Accept: application/vnd.github+json")
    @GET("/user/repos?visibility=all&$PER_PAGE_NAME=$NUMBER_RESULTS_ON_PAGE")
    fun getRepos(@Header("Authorization") accessToken: String,
    ): Single<List<GithubRepoModel>>

    // BRANCHES -------------------------------------------------
    @Headers("Accept: application/vnd.github+json")
    @GET
    fun getBranches(@Header("Authorization") accessToken: String,
                    @Url url: String): Single<List<GithubBrancheModel>>

    // COMMITS -------------------------------------------------
    @Headers("Accept: application/vnd.github+json")
    @GET
    fun getCommits(@Header("Authorization") accessToken: String,
                   @Url url: String
    ): Single<List<GithubCommitModel>>
}
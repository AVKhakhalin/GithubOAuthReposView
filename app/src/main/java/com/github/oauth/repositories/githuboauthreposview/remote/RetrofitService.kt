package com.github.oauth.repositories.githuboauthreposview.remote

import com.github.oauth.repositories.githuboauthreposview.model.GithubBrancheModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Url

interface RetrofitService {

    @GET
    fun getToken(@Url url: String): Single<String>

    @GET("/users/{user_login}")
    fun getUser(@Path("user_login") userLogin: String): Single<GithubUserModel>

//    @GET("/user/repos")
    @GET("/users/{user_login}/repos")
    fun getRepos(@Header("access_token") accessToken: String,
                 @Path("user_login") userLogin: String): Single<List<GithubRepoModel>>
//                 ): Single<List<GithubRepoModel>>

    @GET
    fun getBranches(@Header("access_token") accessToken: String,
                    @Url url: String): Single<List<GithubBrancheModel>>

    @GET
    fun getCommits(@Header("access_token") accessToken: String,
                   @Url url: String): Single<List<GithubCommitModel>>
}
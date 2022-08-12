package com.github.oauth.repositories.githuboauthreposview.domain

import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import io.reactivex.rxjava3.core.Single

interface GithubRepoRepository {
    fun getRepos(userLogin: String): Single<List<GithubRepoModel>>
}
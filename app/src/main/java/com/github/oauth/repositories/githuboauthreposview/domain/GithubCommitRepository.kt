package com.github.oauth.repositories.githuboauthreposview.domain

import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import io.reactivex.rxjava3.core.Single

interface GithubCommitRepository {
    fun getCommits(userLogin: String, repoName: String
    ): Single<List<GithubCommitModel>>
}
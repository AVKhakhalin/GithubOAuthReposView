package com.github.oauth.repositories.githuboauthreposview.domain

import com.github.oauth.repositories.githuboauthreposview.view.forks.ForksView

interface GithubCommitRepository {
    fun getCommits(repoId: Int, forksView: ForksView)
}
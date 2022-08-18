package com.github.oauth.repositories.githuboauthreposview.domain.cache

import com.github.oauth.repositories.githuboauthreposview.view.forks.ForksView

interface GithubCommitCache {
    fun getCacheCommit(repoId: Int, forksView: ForksView)
}
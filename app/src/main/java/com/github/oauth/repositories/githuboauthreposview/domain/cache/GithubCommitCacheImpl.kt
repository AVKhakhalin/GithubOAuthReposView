package com.github.oauth.repositories.githuboauthreposview.domain.cache

import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.model.*
import io.reactivex.rxjava3.core.Single

class GithubCommitCacheImpl(
    private val db: AppDatabase
): GithubCommitCache {
    override fun getCacheCommit(repoId: String): Single<List<GithubCommitModel>> {
        return db.commitDao.getByRepoId(repoId)
            .map { list ->
                list.map { commit ->
                    GithubCommitModel(
                        commit.id, GithubCommitCommitInfoModel(commit.message,
                            RoomGithubCommitInfoAuthorModel(commit.authorName, commit.date))
                    )
                }
            }
    }
}
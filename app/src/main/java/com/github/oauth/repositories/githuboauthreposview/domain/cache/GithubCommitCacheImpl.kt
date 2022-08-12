package com.github.oauth.repositories.githuboauthreposview.domain.cache

import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.model.*
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GithubCommitCacheImpl(
    private val db: AppDatabase
): GithubCommitCache {
    override fun getCacheCommit(repoName: String): Single<List<GithubCommitModel>> {
        return db.commitDao.getByRepoName(repoName)
            .subscribeOn(Schedulers.io())
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
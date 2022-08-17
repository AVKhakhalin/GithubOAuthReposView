package com.github.oauth.repositories.githuboauthreposview.domain.cache

import android.util.Log
import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitCommitInfoModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import com.github.oauth.repositories.githuboauthreposview.model.RoomGithubCommitInfoAuthorModel
import com.github.oauth.repositories.githuboauthreposview.utils.LOG_TAG
import com.github.oauth.repositories.githuboauthreposview.view.forks.ForksView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GithubCommitCacheImpl(
    private val db: AppDatabase
): GithubCommitCache {
    override fun getCacheCommit(userLogin: String, repoName: String, forksView: ForksView) {
        db.roomCommitDao.getByLoginAndRepoName(userLogin, repoName)
            .flatMap {
                return@flatMap Single.just(
                    it.map { commit ->
                    GithubCommitModel(
                        commit.id, GithubCommitCommitInfoModel(commit.message,
                            RoomGithubCommitInfoAuthorModel(commit.authorName, commit.date))
                    )
                })
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ commitsList ->
                val resultCommitsList: MutableList<GithubCommitModel> = mutableListOf()
                commitsList.forEach { resultCommitsList.add(it) }
                resultCommitsList.sortBy { it.commit.author.date }
                forksView.showCommits(resultCommitsList)
            }) { error ->
                Log.d(LOG_TAG, "${error.message}")
            }
    }
}
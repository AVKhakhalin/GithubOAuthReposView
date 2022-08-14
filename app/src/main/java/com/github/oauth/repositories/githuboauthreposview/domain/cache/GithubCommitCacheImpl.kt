package com.github.oauth.repositories.githuboauthreposview.domain.cache

import android.util.Log
import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitCommitInfoModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import com.github.oauth.repositories.githuboauthreposview.model.RoomGithubCommitInfoAuthorModel
import com.github.oauth.repositories.githuboauthreposview.utils.LOG_TAG
import com.github.oauth.repositories.githuboauthreposview.view.forks.ForksView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class GithubCommitCacheImpl(
    private val db: AppDatabase
): GithubCommitCache {
    override fun getCacheCommit(repoName: String, forksView: ForksView) {
        val commitsList: MutableList<GithubCommitModel> = mutableListOf()
        db.commitDao.getByRepoName(repoName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { list ->
                list.map { commit ->
                    commitsList.add(GithubCommitModel(
                        commit.id, GithubCommitCommitInfoModel(commit.message,
                            RoomGithubCommitInfoAuthorModel(commit.authorName, commit.date))
                    ))
                }
            }
            .subscribe({
                //Do something on successful completion of all requests
                forksView.showCommits(commitsList)
            }) {
                //Do something on error completion of requests
                Log.d(LOG_TAG, "${it.message}")
            }
    }
}
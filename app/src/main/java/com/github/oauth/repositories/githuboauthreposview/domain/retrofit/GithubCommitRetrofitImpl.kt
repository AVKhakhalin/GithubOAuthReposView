package com.github.oauth.repositories.githuboauthreposview.domain.retrofit

import android.util.Log
import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomGithubCommit
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import com.github.oauth.repositories.githuboauthreposview.remote.RetrofitService
import com.github.oauth.repositories.githuboauthreposview.utils.BASE_API_REPO_URL
import com.github.oauth.repositories.githuboauthreposview.utils.LOG_TAG
import com.github.oauth.repositories.githuboauthreposview.view.forks.ForksView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class GithubCommitRetrofitImpl(
    private val retrofitService: RetrofitService,
    private val db: AppDatabase
): GithubCommitRetrofit {
    override fun getRetrofitCommit(userLogin: String, repoName: String, forksView: ForksView) {
        // Получение списка веток
        val branchesList: MutableList<String> = mutableListOf()
        val branchesUrl: String =
            "$BASE_API_REPO_URL/$userLogin/$repoName/branches"
        Log.d(LOG_TAG, branchesUrl)
        retrofitService.getBranches(branchesUrl)
            .subscribeOn(Schedulers.single())
            .map { branches ->
                val branchesNames = branches.map { branch ->
                    branch.name
                }
                branchesNames.forEach { branchesList.add(it) }
            }
            .subscribe({
                //Do something on successful completion of all requests
                var commitsList: List<GithubCommitModel> = listOf()

                // Получение списка коммитов для каждой ветки
                branchesList.forEach { branch ->
                    val commitsUrl: String =
                        "$BASE_API_REPO_URL/$userLogin/$repoName/commits?sha=$branch"
                    retrofitService.getCommits(commitsUrl)
                        .subscribeOn(Schedulers.io())
                        .flatMap { commits ->
                            commitsList = commits
                            val dbCommits = commits.map {
                                RoomGithubCommit(it.sha, repoName, it.commit.message,
                                    it.commit.author.name, it.commit.author.date)
                            }
                            db.commitDao.insert(dbCommits)
                                .toSingle { commits }
                        }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            //Do something on successful completion of all requests
                            forksView.showCommits(commitsList)
                        }) {
                            //Do something on error completion of requests
                            Log.d(LOG_TAG, "${it.message}")
                        }
                }
            }) {
                //Do something on error completion of requests
                Log.d(LOG_TAG, "${it.message}")
            }
    }
}
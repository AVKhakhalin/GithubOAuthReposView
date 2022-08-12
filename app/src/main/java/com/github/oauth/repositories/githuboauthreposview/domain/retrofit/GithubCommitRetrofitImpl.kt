package com.github.oauth.repositories.githuboauthreposview.domain.retrofit

import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomGithubCommit
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import com.github.oauth.repositories.githuboauthreposview.remote.RetrofitService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Callable


class GithubCommitRetrofitImpl(
    private val retrofitService: RetrofitService,
    private val db: AppDatabase
): GithubCommitRetrofit {
    override fun getRetrofitCommit(userLogin: String, repoName: String
    ): Single<List<GithubCommitModel>> {
        // Получение списка веток
        val branchesList: MutableList<String> = mutableListOf()
        val branchesUrl: String =
            "https://api.github.com/repos/$userLogin/$repoName/branches"
        retrofitService.getBranches(branchesUrl)
            .subscribeOn(Schedulers.io())
            .map { branches ->
                val branchesNames = branches.map { branch ->
                    branch.name
                }
                branchesNames.forEach { branchesList.add(it) }
            }
        // Получение списка коммитов для каждой ветки
        var commitsList: List<GithubCommitModel> = listOf()
        branchesList.forEach { branch ->
            val commitsUrl: String =
                "https://api.github.com/repos/$userLogin/$repoName/commits?sha=$branch"
            retrofitService.getCommits(commitsUrl)
                .subscribeOn(Schedulers.io())
                .flatMap { commits ->
                    commitsList = commits
                    val dbCommits = commits.map {
                        RoomGithubCommit(it.sha, repoName, it.owner.message,
                        it.owner.author.name, it.owner.author.date)
                    }
                    db.commitDao.insert(dbCommits)
                        .toSingle { commits }
                }
        }

        return Single.fromCallable(
                Callable<List<GithubCommitModel>> { commitsList })
                    .subscribeOn(Schedulers.io())
    }
}
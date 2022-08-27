package com.github.oauth.repositories.githuboauthreposview.domain.retrofit

import android.util.Log
import com.github.oauth.repositories.githuboauthreposview.R
import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomCommit
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitCommitInfoModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import com.github.oauth.repositories.githuboauthreposview.model.RoomGithubCommitInfoAuthorModel
import com.github.oauth.repositories.githuboauthreposview.remote.RetrofitService
import com.github.oauth.repositories.githuboauthreposview.utils.*
import com.github.oauth.repositories.githuboauthreposview.utils.resources.ResourcesProvider
import com.github.oauth.repositories.githuboauthreposview.view.forks.ForksView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.atomic.AtomicInteger

class GithubCommitRetrofitImpl(
    private val retrofitService: RetrofitService,
    private val db: AppDatabase,
    private val userChoose: UserChooseRepository,
    private val resourcesProvider: ResourcesProvider
): GithubCommitRetrofit {
    override fun getRetrofitCommit(repoId: Int, forksView: ForksView) {
        // Получение списка веток
        val branchesList: MutableList<String> = mutableListOf()
        val branchesUrl: String =
            "$BASE_API_REPO_URL/${userChoose.getGithubUserModel().login}/${
                userChoose.getGithubRepoModel().name}/$BRANCHES_NAME"
        retrofitService.getBranches("$TOKEN_NAME ${userChoose.getToken()}", branchesUrl)
            .map { branches ->
                val branchesNames = branches.map { branch ->
                    branch.name
                }
                branchesNames.forEach { branchesList.add(it) }
            }
            .subscribeOn(Schedulers.single())
            .observeOn(Schedulers.single())
            .subscribe({
                // Действия в случае успешной загрузке веток репозитория
                val resultCommitsList: MutableList<GithubCommitModel> = mutableListOf()
                val numberElaboratedBranches: AtomicInteger = AtomicInteger(0)
                val uniqueElements: MutableSet<GithubCommitModel> = mutableSetOf()
                // Получение списка коммитов для каждой ветки
                if (branchesList.size > 0) {
                    // Случай, если есть хоть одна ветка
                    branchesList.forEach { branch ->
                        val commitsUrl: String =
                            "$BASE_API_REPO_URL/${userChoose.getGithubUserModel().login}/${
                                userChoose.getGithubRepoModel().name
                            }/$COMMITS_NAME?$SHA_NAME=$branch"
                        retrofitService.getCommits(
                            "$TOKEN_NAME ${userChoose.getToken()}", commitsUrl)
                            .flatMap { commits ->
                                val dbCommits = commits.map {
                                    RoomCommit(
                                        it.sha, repoId, it.commit.message,
                                        it.commit.author.name, it.commit.author.date
                                    )
                                }
                                var prevNumbers: Int = uniqueElements.size
                                commits.forEach {
                                    uniqueElements.add(it)
                                    if (prevNumbers != uniqueElements.size) {
                                        resultCommitsList.add(it)
                                        prevNumbers = uniqueElements.size
                                    }
                                }
                                // Удаление старых данных о коммитах для текущего репозитория
                                db.roomCommitDao.deleteByRepoId(repoId)
                                // Добавление новых данных о коммитах для текущего репозитория
                                db.roomCommitDao.insert(dbCommits)
                                    .toSingle { commits }
                            }
                            .subscribeOn(Schedulers.single())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                // Действия при успешной загрузке коммитов
                                if (numberElaboratedBranches.incrementAndGet() ==
                                    branchesList.size) {
                                    // Сохранение признака загрузки информации
                                    // о коммитах для данного репозитория с сайта github.com
                                    userChoose.setIsCommitModelsUpdated(repoId)
                                    // Сортировка полученного списка коммитов по дате
                                    resultCommitsList.sortBy {
                                        it.commit.author.date
                                    }
                                    // Отображение полученных коммитов
                                    forksView.showCommits(resultCommitsList)
                                }
                            }) {
                                // Действия при ошибке в запросе коммитов
                                Log.d(
                                    LOG_TAG, "ОШИБКА КОММИТА 1: ${it.message}, " +
                                            "Ошибка в userChoose: ${userChoose.getResponseCode()}"
                                )
                            }
                    }
                } else {
                    // Случай, если нет веток
                    // Отображение полученных коммитов
                    Observable.just(listOf(GithubCommitModel(
                        resourcesProvider.getString(R.string.no_commits),
                        GithubCommitCommitInfoModel("",
                            RoomGithubCommitInfoAuthorModel("", "")))))
                        .subscribeOn(Schedulers.single())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            forksView.showCommits(it)
                        }
                }
            }) {
                // Действия при ошибке в запросах веток репозитория
                Log.d(LOG_TAG, "ОШИБКА КОММИТА 2: ${it.message}, " +
                    "Ошибка в userChoose: ${userChoose.getResponseCode()}")
            }
    }
}
package com.github.oauth.repositories.githuboauthreposview.domain.retrofit

import android.util.Log
import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomCommit
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import com.github.oauth.repositories.githuboauthreposview.remote.RetrofitService
import com.github.oauth.repositories.githuboauthreposview.utils.BASE_API_REPO_URL
import com.github.oauth.repositories.githuboauthreposview.utils.LOG_TAG
import com.github.oauth.repositories.githuboauthreposview.view.forks.ForksView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.atomic.AtomicInteger

class GithubCommitRetrofitImpl(
    private val retrofitService: RetrofitService,
    private val db: AppDatabase,
    private val userChoose: UserChooseRepository
): GithubCommitRetrofit {
    override fun getRetrofitCommit(repoId: Int, forksView: ForksView) {
        // Получение списка веток
        val branchesList: MutableList<String> = mutableListOf()
        val branchesUrl: String =
            "$BASE_API_REPO_URL/${userChoose.getGithubUserModel().login}/${
                userChoose.getGithubRepoModel().name}/branches"
        Log.d(LOG_TAG, branchesUrl)
        retrofitService.getBranches(branchesUrl)
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
                branchesList.forEach { branch ->
                    val commitsUrl: String =
                        "$BASE_API_REPO_URL/${userChoose.getGithubUserModel().login}/${
                            userChoose.getGithubRepoModel().name}/commits?sha=$branch"
                    retrofitService.getCommits(commitsUrl)
                        .flatMap { commits ->
                            val dbCommits = commits.map {
                                RoomCommit(it.sha, repoId, it.commit.message,
                                    it.commit.author.name, it.commit.author.date)
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
                            if (numberElaboratedBranches.incrementAndGet() == branchesList.size) {
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
                            Log.d(LOG_TAG, "ОШИБКА КОММИТА 1: ${it.message}, " +
                                "Ошибка в userChoose: ${userChoose.getResponseCode()}")
                        }
                }
            }) {
                // Действия при ошибке в запросах веток репозитория
                Log.d(LOG_TAG, "ОШИБКА КОММИТА 2: ${it.message}, " +
                    "Ошибка в userChoose: ${userChoose.getResponseCode()}")
            }
    }
}
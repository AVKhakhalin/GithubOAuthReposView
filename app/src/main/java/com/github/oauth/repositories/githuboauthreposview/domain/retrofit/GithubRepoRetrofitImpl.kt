package com.github.oauth.repositories.githuboauthreposview.domain.retrofit

import android.util.Log
import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomRepo
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.remote.RetrofitService
import com.github.oauth.repositories.githuboauthreposview.utils.LOG_TAG
import com.github.oauth.repositories.githuboauthreposview.utils.TOKEN_NAME
import com.github.oauth.repositories.githuboauthreposview.utils.cutBranches
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GithubRepoRetrofitImpl(
    private val retrofitService: RetrofitService,
    private val db: AppDatabase,
    private val userChoose: UserChooseRepository
): GithubRepoRetrofit {
    override fun getRetrofitRepo(userLogin: String): Single<List<GithubRepoModel>> {
        return retrofitService.getRepos("$TOKEN_NAME ${userChoose.getToken()}")
            .flatMap { repos ->
                val dbRepos = repos.map {
                    RoomRepo(it.id, it.name ?: "",
                        it.description ?: "", it.owner.id ?: "",
                        it.forksCount ?: 0,it.watchers_count ?: 0,
                        it.owner.login ?: "", it.owner.avatar_url  ?: "",
                        cutBranches(it.branches_url), userChoose.getUserId())
                }
                // Установка признака обновления информации о репозиториях пользователя
                userChoose.setIsRepoModelListUpdated(true)
                // Удаление старой информации о репозиториях пользователя
                db.repoDao.deleteByUserLogin(userLogin)
                // Удаление из списка репозиториев, владельцем которых пользователь не является
                // (доступ к ним через API не получить, а информация видна,
                // потому что пользователю разрешили с ними работать)
                val correctRepos: MutableList<GithubRepoModel> = mutableListOf()
                val correctDBRepos: MutableList<RoomRepo> = mutableListOf()
                dbRepos.forEachIndexed { index, it ->
                    if (it.login == userChoose.getGithubUserModel().login) {
                        correctDBRepos.add(it)
                        correctRepos.add(repos[index])
                    }
                }
                Log.d(LOG_TAG, "${correctDBRepos.size}")
                // Добавление новой информации о репозиториях пользователя
                db.repoDao.insert(correctDBRepos)
                .toSingle { correctRepos.toList() }
            }
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                // Действия в случае ошибки в получении списка репозиториев
                Log.d(LOG_TAG, "ОШИБКА РЕПОЗИТОРИЯ: ${it.message}")
            }
    }
}
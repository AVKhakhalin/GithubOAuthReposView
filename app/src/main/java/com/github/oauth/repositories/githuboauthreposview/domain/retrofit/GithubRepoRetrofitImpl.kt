package com.github.oauth.repositories.githuboauthreposview.domain.retrofit

import android.util.Log
import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomRepo
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.remote.RetrofitService
import com.github.oauth.repositories.githuboauthreposview.utils.LOG_TAG
import com.github.oauth.repositories.githuboauthreposview.utils.cutBranches
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GithubRepoRetrofitImpl(
    private val retrofitService: RetrofitService,
    private val db: AppDatabase
): GithubRepoRetrofit {
    override fun getRetrofitRepo(userLogin: String): Single<List<GithubRepoModel>> {
        return retrofitService.getRepos(userLogin)
            .flatMap { repos ->
                val dbRepos = repos.map {
                    RoomRepo(it.id, it.name ?: "",
                        it.description ?: "", it.owner.id ?: "",
                        it.forksCount ?: 0,it.watchers_count ?: 0,
                        it.owner.login ?: "", it.owner.avatar_url  ?: "",
                        cutBranches(it.branches_url))
                }
                db.repoDao.insert(dbRepos)
                .toSingle { repos }
            }
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Log.d(LOG_TAG, "ОШИБКА РЕПОЗИТОРИЯ: ${it.message}")
            }
    }
}
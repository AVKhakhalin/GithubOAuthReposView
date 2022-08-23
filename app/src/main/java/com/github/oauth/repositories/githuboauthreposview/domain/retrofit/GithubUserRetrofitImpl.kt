package com.github.oauth.repositories.githuboauthreposview.domain.retrofit

import android.util.Log
import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomUser
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import com.github.oauth.repositories.githuboauthreposview.remote.RetrofitService
import com.github.oauth.repositories.githuboauthreposview.utils.LOG_TAG
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GithubUserRetrofitImpl(
    private val retrofitService: RetrofitService,
    private val db: AppDatabase,
): GithubUserRetrofit {
    override fun getRetrofitUser(userLogin: String): Single<GithubUserModel> {
        return retrofitService.getUser(userLogin)
            .flatMap { user ->
                val roomUser = RoomUser(user.id, user.login, user.avatarUrl, user.reposUrl)
                db.userDao.insert(roomUser)
                    .toSingle { user }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Log.d(LOG_TAG, "ОШИБКА ПОЛЬЗОВАТЕЛЯ: ${it.message}")
            }
    }
}
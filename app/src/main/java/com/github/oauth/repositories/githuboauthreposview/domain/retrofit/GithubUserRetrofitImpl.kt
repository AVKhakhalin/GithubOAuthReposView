package com.github.oauth.repositories.githuboauthreposview.domain.retrofit

import android.util.Log
import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomUser
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import com.github.oauth.repositories.githuboauthreposview.remote.RetrofitService
import com.github.oauth.repositories.githuboauthreposview.utils.BASE_API_REPO_URL
import com.github.oauth.repositories.githuboauthreposview.utils.BASE_TOKEN_URL
import com.github.oauth.repositories.githuboauthreposview.utils.LOG_TAG
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GithubUserRetrofitImpl(
    private val retrofitService: RetrofitService,
    private val db: AppDatabase,
    private val userChoose: UserChooseRepository
): GithubUserRetrofit {
    override fun getRetrofitUser(userLogin: String): Single<GithubUserModel> {
        return retrofitService.getUser(userLogin)
            .flatMap { user ->
                val roomUser = RoomUser(user.id, user.login, user.avatarUrl, user.reposUrl)
                // Установка признака обновления данных
                userChoose.setIsUserModelUpdated(true)
                // Сохранение пользовательских данных в базу данных
                db.userDao.insert(roomUser)
                    .toSingle { user }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                // Действия в случае ошибки в получении информации о пользователе
                Log.d(LOG_TAG, "ОШИБКА ПОЛЬЗОВАТЕЛЯ: ${it.message}")
            }
    }

    override fun getRetrofitToken(userLogin: String): Single<String> {
        val tokenUrl: String = "$BASE_TOKEN_URL${userChoose.getGithubUserModel().login}"
        return retrofitService.getToken(tokenUrl)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                // Действия в случае ошибки в получении информации о пользователе
                Log.d(LOG_TAG, "ОШИБКА ПОЛУЧЕНИЯ ТОКЕНА: ${it.message}")
            }
    }
}
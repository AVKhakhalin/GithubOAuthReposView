package com.github.oauth.repositories.githuboauthreposview.domain.retrofit

import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomGithubUser
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import com.github.oauth.repositories.githuboauthreposview.remote.RetrofitService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GithubUserRetrofitImpl(
    private val retrofitService: RetrofitService,
    private val db: AppDatabase,
): GithubUserRetrofit {
    override fun getRetrofitUser(userLogin: String): Single<GithubUserModel> {
        return retrofitService.getUser(userLogin)
            .subscribeOn(Schedulers.io())
            .flatMap { user ->
                val roomUser = RoomGithubUser(user.id, user.login, user.avatarUrl, user.reposUrl)
                db.userDao.insert(roomUser)
                    .toSingle { user }
            }
    }
}
package com.github.oauth.repositories.githuboauthreposview.domain.retrofit

import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomGithubUser
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import com.github.oauth.repositories.githuboauthreposview.remote.RetrofitService
import io.reactivex.rxjava3.core.Maybe

class GithubUserRetrofitImpl(
    private val retrofitService: RetrofitService,
    private val db: AppDatabase
): GithubUserRetrofit {
    override fun getRetrofitUser(login: String): Maybe<GithubUserModel> {
        return retrofitService.getUser(login)
            .flatMap { user ->
                val roomUser = RoomGithubUser(user.id, user.login, user.avatarUrl, user.reposUrl)
                db.userDao.insert(roomUser)
                    .toMaybe()
            }
    }
}
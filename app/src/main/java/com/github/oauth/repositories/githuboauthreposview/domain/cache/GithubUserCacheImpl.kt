package com.github.oauth.repositories.githuboauthreposview.domain.cache

import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GithubUserCacheImpl(
    private val db: AppDatabase
): GithubUserCache {
    override fun getCacheUser(userLogin: String): Single<GithubUserModel> {
        return db.userDao.getByLogin(userLogin)
            .subscribeOn(Schedulers.io())
            .map { roomModel ->
                GithubUserModel(roomModel.id, roomModel.login,
                    roomModel.avatarUrl, roomModel.reposUrl)
            }
                .toSingle()
    }
}
package com.github.oauth.repositories.githuboauthreposview.domain.cache

import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

class GithubUserCacheImpl(
    private val db: AppDatabase
): GithubUserCache {
    override fun getCacheUser(login: String): Single<GithubUserModel> {
        return db.userDao.getByLogin(login)
            .map { roomModel ->
                GithubUserModel(roomModel.id, roomModel.login,
                    roomModel.avatarUrl, roomModel.reposUrl)
            }
                .toSingle()
    }
}
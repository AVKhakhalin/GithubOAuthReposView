package com.github.oauth.repositories.githuboauthreposview.domain.cache

import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoOwnerModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import io.reactivex.rxjava3.core.Single

class GithubRepoCacheImpl(
    private val db: AppDatabase
): GithubRepoCache {
    override fun getCacheRepo(userModel: GithubUserModel): Single<List<GithubRepoModel>> {
        return db.repositoryDao.getByUserId(userModel.id)
            .map { list ->
                list.map { repo ->
                    GithubRepoModel(
                        repo.id, repo.name, repo.description,
                        GithubRepoOwnerModel(repo.userId, repo.login, repo.avatarUrl),
                        repo.branches_url, repo.forksCount, repo.watchers
                    )
                }
            }
    }
}
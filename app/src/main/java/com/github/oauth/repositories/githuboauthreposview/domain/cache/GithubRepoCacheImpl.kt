package com.github.oauth.repositories.githuboauthreposview.domain.cache

import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoOwnerModel
import io.reactivex.rxjava3.core.Single

class GithubRepoCacheImpl(
    private val db: AppDatabase
): GithubRepoCache {
    override fun getCacheRepo(userLogin: String): Single<List<GithubRepoModel>> {
        return db.repositoryDao.getByUserLogin(userLogin)
            .map { list ->
                list.map { repo ->
                    GithubRepoModel(
                        repo.id, repo.name, repo.description,
                        GithubRepoOwnerModel(repo.userId, repo.login, repo.avatarUrl),
                        repo.branchesUrl, repo.forksCount, repo.watchers
                    )
                }
            }
    }
}
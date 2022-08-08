package com.github.oauth.repositories.githuboauthreposview.domain.retrofit

import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomGithubRepo
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import com.github.oauth.repositories.githuboauthreposview.remote.RetrofitService
import io.reactivex.rxjava3.core.Single

class GithubRepoRetrofitImpl(
    private val retrofitService: RetrofitService,
    private val db: AppDatabase
): GithubRepoRetrofit {
    override fun getRetrofitRepo(userModel: GithubUserModel): Single<List<GithubRepoModel>> {
        return retrofitService.getRepos(userModel.reposUrl)
            .flatMap { repos ->
                val dbRepos = repos.map {
                    RoomGithubRepo(it.id, it.name, it.description, it.owner.id, it.forksCount,
                        it.watchers_count, it.owner.login, it.owner.avatarUrl, it.branches_url)
                }
                db.repositoryDao.insert(dbRepos)
                    .toSingle { repos }
            }
    }
}
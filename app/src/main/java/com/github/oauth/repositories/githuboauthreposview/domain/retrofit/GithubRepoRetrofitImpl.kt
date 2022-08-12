package com.github.oauth.repositories.githuboauthreposview.domain.retrofit

import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.db.model.RoomGithubRepo
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.remote.RetrofitService
import io.reactivex.rxjava3.core.Single

class GithubRepoRetrofitImpl(
    private val retrofitService: RetrofitService,
    private val db: AppDatabase
): GithubRepoRetrofit {
    override fun getRetrofitRepo(userLogin: String): Single<List<GithubRepoModel>> {
        return retrofitService.getRepos(userLogin)
            .flatMap { repos ->
                val dbRepos = repos.map {
                    RoomGithubRepo(it.id ?: "", it.name ?: "",
                        it.description ?: "", it.owner.id ?: "",
                        it.forksCount ?: 0,it.watchers_count ?: 0,
                        it.owner.login ?: "", it.owner.avatar_url  ?: "",
                        cutBranches(it.branches_url))
                }
                db.repositoryDao.insert(dbRepos)
                    .toSingle { repos }
            }
    }

    // Обрезка концовки ссылки на ветки репозитория в строке "{/branch}"
    private fun cutBranches(url: String?): String {
        return url?.substring(0, url.indexOf("{/branch}")) ?: ""
    }
}
package com.github.oauth.repositories.githuboauthreposview.di.modules

import com.github.oauth.repositories.githuboauthreposview.app.App
import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.di.scope.ReposScope
import com.github.oauth.repositories.githuboauthreposview.di.scope.containers.ReposScopeContainer
import com.github.oauth.repositories.githuboauthreposview.domain.GithubRepoRepository
import com.github.oauth.repositories.githuboauthreposview.domain.GithubRepoRepositoryImpl
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
import com.github.oauth.repositories.githuboauthreposview.domain.cache.GithubRepoCache
import com.github.oauth.repositories.githuboauthreposview.domain.cache.GithubRepoCacheImpl
import com.github.oauth.repositories.githuboauthreposview.domain.retrofit.GithubRepoRetrofit
import com.github.oauth.repositories.githuboauthreposview.domain.retrofit.GithubRepoRetrofitImpl
import com.github.oauth.repositories.githuboauthreposview.remote.RetrofitService
import com.github.oauth.repositories.githuboauthreposview.utils.connectivity.NetworkStatus
import dagger.Module
import dagger.Provides

@Module
abstract class GithubReposModule {
    companion object {
        @ReposScope
        @Provides
        fun reposRepo(
            networkStatus: NetworkStatus,
            githubRepoRetrofit: GithubRepoRetrofit,
            githubRepoCache: GithubRepoCache,
            userChoose: UserChooseRepository
        ): GithubRepoRepository {
            return GithubRepoRepositoryImpl(networkStatus, githubRepoRetrofit,
                githubRepoCache, userChoose)
        }

        @ReposScope
        @Provides
        fun reposRetrofit(
            retrofitService: RetrofitService,
            db: AppDatabase,
            userChoose: UserChooseRepository
        ): GithubRepoRetrofit {
            return GithubRepoRetrofitImpl(retrofitService, db, userChoose)
        }

        @ReposScope
        @Provides
        fun reposCache(
            db: AppDatabase
        ): GithubRepoCache {
            return GithubRepoCacheImpl(db)
        }

        @ReposScope
        @Provides
        fun scopeContainer(app: App): ReposScopeContainer = app
    }
}
package com.github.oauth.repositories.githuboauthreposview.di.modules

import com.github.oauth.repositories.githuboauthreposview.app.App
import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.di.scope.ForksScope
import com.github.oauth.repositories.githuboauthreposview.di.scope.containers.ForksScopeContainer
import com.github.oauth.repositories.githuboauthreposview.domain.GithubCommitRepository
import com.github.oauth.repositories.githuboauthreposview.domain.GithubCommitRepositoryImpl
import com.github.oauth.repositories.githuboauthreposview.domain.cache.GithubCommitCache
import com.github.oauth.repositories.githuboauthreposview.domain.cache.GithubCommitCacheImpl
import com.github.oauth.repositories.githuboauthreposview.domain.retrofit.GithubCommitRetrofit
import com.github.oauth.repositories.githuboauthreposview.domain.retrofit.GithubCommitRetrofitImpl
import com.github.oauth.repositories.githuboauthreposview.remote.RetrofitService
import com.github.oauth.repositories.githuboauthreposview.utils.connectivity.NetworkStatus
import dagger.Module
import dagger.Provides

@Module
abstract class GithubForksModule {

    companion object {
        @ForksScope
        @Provides
        fun commitsRepo(
            networkStatus: NetworkStatus,
            githubCommitRetrofit: GithubCommitRetrofit,
            githubCommitCache: GithubCommitCache
        ): GithubCommitRepository {
            return GithubCommitRepositoryImpl(networkStatus, githubCommitRetrofit, githubCommitCache)
        }

        @ForksScope
        @Provides
        fun reposRetrofit(
            retrofitService: RetrofitService,
            db: AppDatabase
        ): GithubCommitRetrofit {
            return GithubCommitRetrofitImpl(retrofitService, db)
        }

        @ForksScope
        @Provides
        fun reposCache(
            db: AppDatabase
        ): GithubCommitCache {
            return GithubCommitCacheImpl(db)
        }

        @ForksScope
        @Provides
        fun forksScopeContainer(app: App): ForksScopeContainer = app
    }
}
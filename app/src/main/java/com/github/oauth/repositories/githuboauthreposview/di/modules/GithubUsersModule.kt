package com.github.oauth.repositories.githuboauthreposview.di.modules

import com.github.oauth.repositories.githuboauthreposview.app.App
import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.di.scope.UsersScope
import com.github.oauth.repositories.githuboauthreposview.di.scope.containers.UsersScopeContainer
import com.github.oauth.repositories.githuboauthreposview.domain.GithubUserRepository
import com.github.oauth.repositories.githuboauthreposview.domain.GithubUserRepositoryImpl
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
import com.github.oauth.repositories.githuboauthreposview.domain.cache.GithubUserCache
import com.github.oauth.repositories.githuboauthreposview.domain.cache.GithubUserCacheImpl
import com.github.oauth.repositories.githuboauthreposview.domain.retrofit.GithubUserRetrofit
import com.github.oauth.repositories.githuboauthreposview.domain.retrofit.GithubUserRetrofitImpl
import com.github.oauth.repositories.githuboauthreposview.remote.RetrofitService
import com.github.oauth.repositories.githuboauthreposview.utils.connectivity.NetworkStatus
import com.github.oauth.repositories.githuboauthreposview.utils.resources.ResourcesProvider
import dagger.Module
import dagger.Provides

@Module
abstract class GithubUsersModule {

    companion object {
        @UsersScope
        @Provides
        fun usersRepo(
            networkStatus: NetworkStatus,
            githubUserRetrofit: GithubUserRetrofit,
            githubUsersCache: GithubUserCache,
            userChoose: UserChooseRepository,
            resourcesProvider: ResourcesProvider
        ): GithubUserRepository {
            return GithubUserRepositoryImpl(networkStatus, githubUserRetrofit,
                githubUsersCache, userChoose, resourcesProvider)
        }

        @UsersScope
        @Provides
        fun usersRetrofit(
            retrofitService: RetrofitService,
            db: AppDatabase,
            userChoose: UserChooseRepository
        ): GithubUserRetrofit {
            return GithubUserRetrofitImpl(retrofitService, db, userChoose)
        }

        @UsersScope
        @Provides
        fun usersCache(
            db: AppDatabase
        ): GithubUserCache {
            return GithubUserCacheImpl(db)
        }

        @UsersScope
        @Provides
        fun scopeContainer(app: App): UsersScopeContainer = app
    }
}
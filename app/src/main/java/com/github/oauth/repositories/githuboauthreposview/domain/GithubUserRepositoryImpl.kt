package com.github.oauth.repositories.githuboauthreposview.domain

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.github.oauth.repositories.githuboauthreposview.domain.cache.GithubUserCache
import com.github.oauth.repositories.githuboauthreposview.domain.retrofit.GithubUserRetrofit
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import com.github.oauth.repositories.githuboauthreposview.utils.SHARED_PREFERENCES_AC_TOK
import com.github.oauth.repositories.githuboauthreposview.utils.SHARED_PREFERENCES_KEY
import com.github.oauth.repositories.githuboauthreposview.utils.connectivity.NetworkStatus
import com.github.oauth.repositories.githuboauthreposview.utils.resources.ResourcesProvider
import io.reactivex.rxjava3.core.Single


class GithubUserRepositoryImpl(
    private val networkStatus: NetworkStatus,
    private val githubUserRetrofit: GithubUserRetrofit,
    private val githubUserCache: GithubUserCache,
    private val userChoose: UserChooseRepository,
    private val resourcesProvider: ResourcesProvider
): GithubUserRepository {
    private val sharedPreferences: SharedPreferences = resourcesProvider.getContext().
    getSharedPreferences(SHARED_PREFERENCES_KEY, AppCompatActivity.MODE_PRIVATE)

    override fun getUser(login: String): Single<GithubUserModel> {
        return if ((networkStatus.isOnline()) && (!userChoose.getIsUserModelUpdated()))
            githubUserRetrofit.getRetrofitUser(userChoose.getToken(), login)
        else
            githubUserCache.getCacheUser(login)
    }

    override fun getToken(login: String): Single<String> {
        return if ((networkStatus.isOnline()) && (login.isNotEmpty()))
            githubUserRetrofit.getRetrofitToken(login)
        else
            Single.just(sharedPreferences.getString(SHARED_PREFERENCES_AC_TOK, "").toString())
    }
}
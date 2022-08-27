package com.github.oauth.repositories.githuboauthreposview.view.users

import android.util.Log
import com.github.oauth.repositories.githuboauthreposview.R
import com.github.oauth.repositories.githuboauthreposview.di.scope.containers.UsersScopeContainer
import com.github.oauth.repositories.githuboauthreposview.domain.GithubUserRepository
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import com.github.oauth.repositories.githuboauthreposview.utils.LOG_TAG
import com.github.oauth.repositories.githuboauthreposview.utils.resources.ResourcesProvider
import com.github.oauth.repositories.githuboauthreposview.view.screens.AppScreens
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class UsersPresenter @Inject constructor(
    private val router: Router,
    private val usersRepository: GithubUserRepository,
    private val appScreens: AppScreens,
    private val userChoose: UserChooseRepository,
    private val usersScopeContainer: UsersScopeContainer,
    private val resourcesProvider: ResourcesProvider
): MvpPresenter<UsersView>() {
    fun getGithubUserInfo(userLogin: String) {
        if (userLogin.isNotEmpty()) {
            userChoose.setGithubUserModel(GithubUserModel("", userLogin,"",""))
            setUserData(userLogin)
        } else userChoose.setGithubUserModel(GithubUserModel("","","",""))
    }

    fun onRepoClicked() {
        router.navigateTo(appScreens.repoScreen())
    }

    fun reloadOAuth() {
        router.replaceScreen(appScreens.usersScreen())
    }

    private fun setUserData(userLogin: String) {
        // Получение пользовательских данных с сайта github.com
        usersRepository.getUser(userLogin)
            .doOnSubscribe { viewState.showLoading() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { user ->
                    userChoose.setGithubUserModel(user)
                    Log.d(LOG_TAG, "${user.id}, ${user.login}, " +
                        "${user.avatarUrl}, ${user.reposUrl}")
                    viewState.hideLoading()
                }, { e ->
                    Log.e(
                        LOG_TAG,
                        resourcesProvider.getString(R.string.error_not_user_data), e
                    )
                    viewState.hideLoading()
                }
            )
        // Получение токена пользователя с сервера OAuth
        if (userChoose.getToken().isEmpty()) {
            usersRepository.getToken(userLogin)
                .doOnSubscribe { viewState.showLoading() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { token ->
                        userChoose.setToken(token)
                    }, { e ->
                        Log.e(
                            LOG_TAG,
                            resourcesProvider.getString(R.string.error_token_not_get), e
                        )
                    }
                )
        }
    }

    /** Уничтожение GithubUsersSubcomponent при уничтожении данного презентера */
    override fun onDestroy() {
        usersScopeContainer.destroyGithubUsersSubcomponent()
        super.onDestroy()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}
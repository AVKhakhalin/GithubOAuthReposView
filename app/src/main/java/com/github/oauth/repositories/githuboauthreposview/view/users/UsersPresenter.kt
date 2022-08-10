package com.github.oauth.repositories.githuboauthreposview.view.users

import android.util.Log
import android.widget.Toast
import com.github.oauth.repositories.githuboauthreposview.R
import com.github.oauth.repositories.githuboauthreposview.di.scope.containers.UsersScopeContainer
import com.github.oauth.repositories.githuboauthreposview.domain.GithubRepoRepository
import com.github.oauth.repositories.githuboauthreposview.domain.GithubUserRepository
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import com.github.oauth.repositories.githuboauthreposview.utils.LOG_TAG
import com.github.oauth.repositories.githuboauthreposview.utils.connectivity.NetworkStatus
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
    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    fun getGithubUserInfo(userLogin: String) {
        if (userLogin.isNotEmpty()) {
            loadData(userLogin)
        } else userChoose.setGithubUserModel(GithubUserModel("","","",""))
    }

    fun onRepoClicked() {
        router.navigateTo(appScreens.repoScreen())
    }

    private fun loadData(userLogin: String) {
        Toast.makeText(resourcesProvider.getContext(), "loadData($userLogin)", Toast.LENGTH_SHORT).show()
        usersRepository.getUser(userLogin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoading() }
            .subscribe(
                { user ->
                    userChoose.setGithubUserModel(user)
                    viewState.hideLoading()
                }, { e ->
                    Log.e(
                        LOG_TAG,
                        resourcesProvider.getString(R.string.error_not_user_data), e
                    )
                    viewState.hideLoading()
                }
            )
    }

    /** Уничтожение GithubUsersSubcomponent при уничтожении данного презентера */
    override fun onDestroy() {
        usersScopeContainer.destroyGithubUsersSubcomponent()
        super.onDestroy()
    }
}
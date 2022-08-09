package com.github.oauth.repositories.githuboauthreposview.view.users

import com.github.oauth.repositories.githuboauthreposview.di.scope.containers.UsersScopeContainer
import com.github.oauth.repositories.githuboauthreposview.utils.connectivity.NetworkStatus
import com.github.oauth.repositories.githuboauthreposview.utils.resources.ResourcesProvider
import com.github.oauth.repositories.githuboauthreposview.view.screens.AppScreens
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class UsersPresenter @Inject constructor(
    private val router: Router,
    private val usersScopeContainer: UsersScopeContainer,
): MvpPresenter<UsersView>() {
    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    /** Уничтожение GithubUsersSubcomponent при уничтожении данного презентера */
    override fun onDestroy() {
        usersScopeContainer.destroyGithubUsersSubcomponent()
        super.onDestroy()
    }
}
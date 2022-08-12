package com.github.oauth.repositories.githuboauthreposview.view.forks

import com.github.oauth.repositories.githuboauthreposview.di.scope.containers.ForksScopeContainer
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class ForksPresenter @Inject constructor(
    private val router: Router,
    private val forksScopeContainer: ForksScopeContainer,
    private val userChoose: UserChooseRepository,
): MvpPresenter<ForksView>() {

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    fun onRepoClicked(commit: GithubCommitModel) {
        userChoose.setGithubCommitModel(commit)
    }

    /** Уничтожение ForksSubcomponent при уничтожении данного презентера */
    override fun onDestroy() {
        forksScopeContainer.destroyForksSubcomponent()
        super.onDestroy()
    }
}
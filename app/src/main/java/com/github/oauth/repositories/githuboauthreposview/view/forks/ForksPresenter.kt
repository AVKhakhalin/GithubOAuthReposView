package com.github.oauth.repositories.githuboauthreposview.view.forks

import com.github.oauth.repositories.githuboauthreposview.di.scope.containers.ForksScopeContainer
import com.github.oauth.repositories.githuboauthreposview.domain.GithubCommitRepository
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import com.github.oauth.repositories.githuboauthreposview.utils.connectivity.NetworkStatus
import com.github.oauth.repositories.githuboauthreposview.utils.resources.ResourcesProvider
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class ForksPresenter @Inject constructor(
    private val router: Router,
    private val networkStatus: NetworkStatus,
    private val forksScopeContainer: ForksScopeContainer,
    private val userChoose: UserChooseRepository,
    private val commit: GithubCommitRepository,
    private val resourcesProvider: ResourcesProvider
): MvpPresenter<ForksView>() {

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    fun onCommitClicked(commit: GithubCommitModel) {
        userChoose.setGithubCommitModel(commit)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData(userChoose.getGithubRepoModel().id)
    }

    private fun loadData(repoId: Int) {
        commit.getCommits(repoId, viewState)
    }

    /** Уничтожение ForksSubcomponent при уничтожении данного презентера */
    override fun onDestroy() {
        forksScopeContainer.destroyForksSubcomponent()
        super.onDestroy()
    }
}
package com.github.oauth.repositories.githuboauthreposview.view.forks

import android.util.Log
import android.view.View
import com.github.oauth.repositories.githuboauthreposview.R
import com.github.oauth.repositories.githuboauthreposview.di.scope.containers.ForksScopeContainer
import com.github.oauth.repositories.githuboauthreposview.domain.GithubCommitRepository
import com.github.oauth.repositories.githuboauthreposview.domain.GithubRepoRepository
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import com.github.oauth.repositories.githuboauthreposview.utils.LOG_TAG
import com.github.oauth.repositories.githuboauthreposview.utils.connectivity.NetworkStatus
import com.github.oauth.repositories.githuboauthreposview.utils.resources.ResourcesProvider
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
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
        loadData(userChoose.getGithubUserModel().login, userChoose.getGithubRepoModel().name)
    }

    private fun loadData(userLogin: String, repoName: String) {

        commit.getCommits(userLogin, repoName, viewState)

//        commit.getCommits(userLogin, repoName)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe { viewState.showLoading() }
//            .subscribe(
//                { commits ->
//                    viewState.showCommits(commits)
//                    viewState.hideLoading()
//                    userChoose.setGithubCommitsModel(commits)
//                }, {
//                    Log.e(
//                        LOG_TAG,
//                        resourcesProvider.getString(R.string.error_not_repos_List),
//                        it
//                    )
//                    viewState.hideLoading()
//                }
//            )
    }

    /** Уничтожение ForksSubcomponent при уничтожении данного презентера */
    override fun onDestroy() {
        forksScopeContainer.destroyForksSubcomponent()
        super.onDestroy()
    }
}
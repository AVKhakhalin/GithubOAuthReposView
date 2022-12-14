package com.github.oauth.repositories.githuboauthreposview.view.repos

import android.util.Log
import com.github.oauth.repositories.githuboauthreposview.R
import com.github.oauth.repositories.githuboauthreposview.di.scope.containers.ReposScopeContainer
import com.github.oauth.repositories.githuboauthreposview.domain.GithubRepoRepository
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoOwnerModel
import com.github.oauth.repositories.githuboauthreposview.utils.LOG_TAG
import com.github.oauth.repositories.githuboauthreposview.utils.cutBranches
import com.github.oauth.repositories.githuboauthreposview.utils.resources.ResourcesProvider
import com.github.oauth.repositories.githuboauthreposview.view.screens.AppScreens
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class ReposPresenter @Inject constructor(
    private val router: Router,
    private val repo: GithubRepoRepository,
    private val appScreens: AppScreens,
    private val userChoose: UserChooseRepository,
    private val reposScopeContainer: ReposScopeContainer,
    private val resourcesProvider: ResourcesProvider
): MvpPresenter<ReposView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData(userChoose.getGithubUserModel().login)
    }

    private fun loadData(userLogin: String) {
        repo.getRepos(userLogin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoading() }
            .subscribe(
                { repos ->
                    viewState.hideLoading()
                    viewState.showRepos(repos.sortedBy { it.name })
                    userChoose.setGithubReposModel(repos)
                }, {
                    Log.e(
                        LOG_TAG,
                        resourcesProvider.getString(R.string.error_not_repos_List),
                        it
                    )
                    viewState.hideLoading()
                }
            )
    }

    fun onRepoClicked(repo: GithubRepoModel) {
        val correctRepo: GithubRepoModel = GithubRepoModel(repo.id, repo.name,
            repo.description ?: "", GithubRepoOwnerModel(repo.owner.id, repo.owner.login,
                repo.owner.avatar_url), cutBranches(repo.branches_url),
            repo.forksCount, repo.watchers_count)
        userChoose.setGithubRepoModel(correctRepo)
        Log.d(LOG_TAG, "???????????? ?????????????????????? ${correctRepo.name}")
        router.navigateTo(appScreens.forksScreen())
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    /** ?????????????????????? ReposSubcomponent ?????? ?????????????????????? ?????????????? ???????????????????? */
    override fun onDestroy() {
        reposScopeContainer.destroyGithubReposSubcomponent()
        super.onDestroy()
    }
}
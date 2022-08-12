package com.github.oauth.repositories.githuboauthreposview.view.repos

import com.github.oauth.repositories.githuboauthreposview.model.GithubRepoModel
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface ReposView: MvpView {

    fun showLoading()

    fun hideLoading()

    fun showRepos(repos: List<GithubRepoModel>)
}
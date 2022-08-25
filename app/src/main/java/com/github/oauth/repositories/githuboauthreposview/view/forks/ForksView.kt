package com.github.oauth.repositories.githuboauthreposview.view.forks

import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface ForksView: MvpView {
    fun showLoading()

    fun hideLoading()

    fun showCommits(commits: List<GithubCommitModel>)
}
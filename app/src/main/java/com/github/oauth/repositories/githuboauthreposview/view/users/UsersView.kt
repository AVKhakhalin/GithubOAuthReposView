package com.github.oauth.repositories.githuboauthreposview.view.users

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface UsersView: MvpView {

    @AddToEndSingle
    fun getAndSaveUserData(userLogin: String)

    @AddToEndSingle
    fun logoutToGithub()

    @AddToEndSingle
    fun moveToRepositories()

    @AddToEndSingle
    fun showLoading()

    @AddToEndSingle
    fun hideLoading()
}
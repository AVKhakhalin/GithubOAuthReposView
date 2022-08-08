package com.github.oauth.repositories.githuboauthreposview.view.users

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.oauth.repositories.githuboauthreposview.R
import com.github.oauth.repositories.githuboauthreposview.app.App
import com.github.oauth.repositories.githuboauthreposview.databinding.FragmentUsersBinding
import com.github.oauth.repositories.githuboauthreposview.utils.binding.viewBinding
import com.github.oauth.repositories.githuboauthreposview.view.base.BackButtonListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersFragment : MvpAppCompatFragment(R.layout.fragment_users), UsersView, BackButtonListener {

    /** Задание переменных */ //region
    // presenter
    private val presenter by moxyPresenter {
        App.instance.initGithubUsersSubcomponent()
        App.instance.usersSubcomponent?.provideUsersPresenter()!!
    }

    // binding
    private val binding by viewBinding<FragmentUsersBinding>()
    //endregion

    companion object {
        fun newInstance() = UsersFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Установка списка пользователей */

    }


    override fun backPressed(): Boolean {
        presenter.backPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
    }

    override fun loginToGithub() {
        TODO("Not yet implemented")
    }

    override fun logoutToGithub() {
        TODO("Not yet implemented")
    }

    override fun moveToRepositories() {
        TODO("Not yet implemented")
    }
}
package com.github.oauth.repositories.githuboauthreposview.view.screens

import com.github.oauth.repositories.githuboauthreposview.view.users.UsersFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface AppScreens {
    fun usersScreen(): FragmentScreen
}

class AppScreensImpl: AppScreens {
    /** Вызов фрагмента со списком логинов пользователей */
    override fun usersScreen() = FragmentScreen {
        UsersFragment.newInstance()
    }
}
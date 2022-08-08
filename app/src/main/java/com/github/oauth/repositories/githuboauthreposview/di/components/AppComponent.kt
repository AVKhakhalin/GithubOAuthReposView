package com.github.oauth.repositories.githuboauthreposview.di.components

import com.github.oauth.repositories.githuboauthreposview.di.modules.AppModule
import com.github.oauth.repositories.githuboauthreposview.di.modules.CiceroneModule
import com.github.oauth.repositories.githuboauthreposview.di.modules.DbModule
import com.github.oauth.repositories.githuboauthreposview.di.modules.NetworkModule
import com.github.oauth.repositories.githuboauthreposview.view.main.MainActivity
import com.github.oauth.repositories.githuboauthreposview.view.main.MainPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CiceroneModule::class,
        AppModule::class,
        DbModule::class,
        NetworkModule::class
    ]
)

interface AppComponent {
    /** AppComponent */ //region
    fun mainPresenter(): MainPresenter
    fun injectMainActivity(mainActivity: MainActivity)
    //endregion

    /** Subcomponents */ //region
    fun usersSubcomponent(): GithubUsersSubcomponent
    //endregion
}
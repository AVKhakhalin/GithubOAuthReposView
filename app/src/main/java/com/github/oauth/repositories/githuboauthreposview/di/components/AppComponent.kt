package com.github.oauth.repositories.githuboauthreposview.di.components

import com.github.oauth.repositories.githuboauthreposview.di.modules.*
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
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
        NetworkModule::class,
        RepositoryModule::class
    ]
)

interface AppComponent {
    /** AppComponent */ //region
    fun mainPresenter(): MainPresenter
    fun injectMainActivity(mainActivity: MainActivity)
    fun userChoose(): UserChooseRepository
    //endregion

    /** Subcomponents */ //region
    fun usersSubcomponent(): GithubUsersSubcomponent
    fun reposSubcomponent(): GithubReposSubcomponent
    fun forksSubcomponent(): GithubForksSubcomponent
    //endregion
}
package com.github.oauth.repositories.githuboauthreposview.view.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.oauth.repositories.githuboauthreposview.R
import com.github.oauth.repositories.githuboauthreposview.app.App
import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.remote.RetrofitService
import com.github.oauth.repositories.githuboauthreposview.utils.LOG_TAG
import com.github.oauth.repositories.githuboauthreposview.view.base.BackButtonListener
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class MainActivity: MvpAppCompatActivity(R.layout.activity_main), MainView {
    /** Задание переменных */ //region
    // navigatorHolder
    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    // navigator
    private val navigator = AppNavigator(this@MainActivity, R.id.container)
    // moxyPresenter
    private val presenter by moxyPresenter {
        App.instance.appComponent.mainPresenter()
    }
    @Inject
    lateinit var db: AppDatabase
    @Inject
    lateinit var retrofitService: RetrofitService
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.injectMainActivity(this@MainActivity)
        /** Получение разрешений на запись информации */
        presenter.isStoragePermissionGranted(this@MainActivity)


        db.userDao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) // Указание, что результат нужно получить в основном потоке
            .subscribe({
                //Do something on successful completion of all requests
                Toast.makeText(this, "Количество пользователей: ${it.size}", Toast.LENGTH_SHORT).show()
            }) {
                //Do something on error completion of requests
                Log.d(LOG_TAG, "${it.message}")
            }

        db.repoDao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) // Указание, что результат нужно получить в основном потоке
            .subscribe({
                //Do something on successful completion of all requests
                Toast.makeText(this, "Количество репозиториев: ${it.size}", Toast.LENGTH_SHORT).show()
                if (it.isNotEmpty()) {
                    Toast.makeText(this, "Количество репозиториев: ${it[0].login}", Toast.LENGTH_SHORT)
                        .show()
                }
            }) {
                //Do something on error completion of requests
                Log.d(LOG_TAG, "${it.message}")
            }

        db.roomCommitDao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) // Указание, что результат нужно получить в основном потоке
            .subscribe({
                //Do something on successful completion of all requests
                Toast.makeText(this, "Количество коммитов: ${it.size}", Toast.LENGTH_SHORT).show()
            }) {
                //Do something on error completion of requests
                Log.d(LOG_TAG, "${it.message}")
            }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        presenter.backPressed()
    }

    fun showMessage(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
        Log.d(LOG_TAG, message)
    }
}
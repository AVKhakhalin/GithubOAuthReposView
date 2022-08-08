package com.github.oauth.repositories.githuboauthreposview.view.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.github.oauth.repositories.githuboauthreposview.R
import com.github.oauth.repositories.githuboauthreposview.utils.DELAY_TIME
import com.github.oauth.repositories.githuboauthreposview.utils.connectivity.NetworkStatus
import com.github.oauth.repositories.githuboauthreposview.utils.resources.ResourcesProvider
import com.github.oauth.repositories.githuboauthreposview.view.screens.AppScreens
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.*
import moxy.MvpPresenter
import okhttp3.internal.wait
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val router: Router,
    private val appScreens: AppScreens,
    private val networkStatus: NetworkStatus,
    private val resourcesProvider: ResourcesProvider,
): MvpPresenter<MainView>() {

    override fun attachView(view: MainView?) {
        super.attachView(view)
        // Проверка статуса наличия сети
        if (!networkStatus.isOnline()) {
            (view as MainActivity).showMessage(
                resourcesProvider.getString(R.string.github_no_internet)
            )
            restoreAppAfterNetworkOnline()
        } else {
            // Переход на окно с выбором пользователя (авторизацией пользователя)
            router.replaceScreen(appScreens.usersScreen())
        }
    }

    fun backPressed() {
        router.exit()
    }

    /** Получение разрешений на запись и считывание информации с телефона */
    fun isStoragePermissionGranted(mainActivity: MainActivity): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mainActivity.checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                mainActivity.showMessage(
                    mainActivity.getString(R.string.get_permission_write_read_text)
                )
                true
            } else {
                mainActivity.showMessage(
                    mainActivity.getString(R.string.not_get_permission_write_read_text)
                )
                ActivityCompat.requestPermissions(
                    mainActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
                false
            }
        } else {
            mainActivity.showMessage(
                mainActivity.getString(R.string.get_permission_write_read_text)
            )
            true
        }
    }

    // Восстановление работы приложения в случае появления сети Интернет
    private fun restoreAppAfterNetworkOnline() {
        GlobalScope.launch(Dispatchers.IO) {
            while(true) {
                if (networkStatus.isOnline()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(resourcesProvider.getContext(),
                            resourcesProvider.getString(R.string.github_yes_internet),
                            Toast.LENGTH_SHORT).show()
                    }
                    break
                } else {
                    delay(DELAY_TIME)
                }
            }
            // Переход на окно с выбором пользователя (авторизацией пользователя)
            withContext(Dispatchers.Main) {
                router.replaceScreen(appScreens.usersScreen())
            }
        }
    }
}
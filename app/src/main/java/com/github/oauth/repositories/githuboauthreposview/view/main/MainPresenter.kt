package com.github.oauth.repositories.githuboauthreposview.view.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import com.github.oauth.repositories.githuboauthreposview.R
import com.github.oauth.repositories.githuboauthreposview.view.screens.AppScreens
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val router: Router,
    private val appScreens: AppScreens
): MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(appScreens.usersScreen())
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
}
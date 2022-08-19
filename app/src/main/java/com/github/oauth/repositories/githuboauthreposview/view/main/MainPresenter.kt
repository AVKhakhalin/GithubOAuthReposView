package com.github.oauth.repositories.githuboauthreposview.view.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.github.oauth.repositories.githuboauthreposview.R
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
import com.github.oauth.repositories.githuboauthreposview.utils.DELAY_TIME
import com.github.oauth.repositories.githuboauthreposview.utils.URL_TO_PING
import com.github.oauth.repositories.githuboauthreposview.utils.connectivity.NetworkStatus
import com.github.oauth.repositories.githuboauthreposview.utils.resources.ResourcesProvider
import com.github.oauth.repositories.githuboauthreposview.view.screens.AppScreens
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.*
import moxy.MvpPresenter
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

class MainPresenter @Inject constructor(
    private val router: Router,
    private val appScreens: AppScreens,
    private val networkStatus: NetworkStatus,
    private val resourcesProvider: ResourcesProvider,
    private val userChoose: UserChooseRepository
): MvpPresenter<MainView>() {

    override fun attachView(view: MainView?) {
        super.attachView(view)
        // Запуск постоянной проверки наличия сети
        restoreAppAfterNetworkOnline()
        // Переход на окно с авторизацией в случае наличия сети Интернет при старте приложения
        GlobalScope.launch(Dispatchers.IO) {
            if (pingResult())
                withContext(Dispatchers.Main) {
                    router.replaceScreen(appScreens.usersScreen())
                }
        }
        // Уведомление об отсутствии сети Интернет при старте приложения
        if (!networkStatus.isOnline()) {
            (view as MainActivity).showMessage(
                resourcesProvider.getString(R.string.github_no_internet)
            )
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
        var isOnline: Boolean = networkStatus.isOnline()
        GlobalScope.launch(Dispatchers.IO) {
            while(true) {
                if (networkStatus.isOnline()) {
                    if ((!isOnline) && (pingResult())) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                resourcesProvider.getContext(),
                                resourcesProvider.getString(R.string.github_yes_internet),
                                Toast.LENGTH_SHORT
                            ).show()
                            if (userChoose.getGithubUserModel().login.isEmpty()) {
                                // Переход на окно с выбором пользователя (авторизацией пользователя)
                                router.replaceScreen(appScreens.usersScreen())
                            }
                        }
                        isOnline = true
                    }
                } else {
                    if (isOnline) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                resourcesProvider.getContext(),
                                resourcesProvider.getString(R.string.github_no_internet),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        isOnline = false
                    }
                }
                delay(DELAY_TIME)
            }
        }
    }

    // Пингование сайта
    suspend fun pingResult(): Boolean {
        try {
            var stringAnswer = ""
            // Открытие сессии
            var urlConnection: HttpsURLConnection? = null
            val url = URL(URL_TO_PING)
            urlConnection = url.openConnection() as HttpsURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.readTimeout = 10000
            val inf = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val answer = StringBuilder()
            // Распознавание ответа
            var line: String?
            while (inf.readLine().also { line = it } != null) {
                answer.append(line).append('\n')
            }
            stringAnswer = answer.toString()
            // Закрытие сессии
            urlConnection.disconnect()
            // Сохранение результата
            return stringAnswer.isNotEmpty()
        } catch (e: Exception) {
            Log.d("", e.localizedMessage)
            return false
        }
    }
}
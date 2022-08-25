package com.github.oauth.repositories.githuboauthreposview.view.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.github.oauth.repositories.githuboauthreposview.R
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
import com.github.oauth.repositories.githuboauthreposview.utils.*
import com.github.oauth.repositories.githuboauthreposview.utils.connectivity.NetworkStatus
import com.github.oauth.repositories.githuboauthreposview.utils.resources.ResourcesProvider
import com.github.oauth.repositories.githuboauthreposview.view.screens.AppScreens
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.*
import moxy.MvpPresenter
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
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
    /* Исходные данные */ // region
    // Scope для корутин
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    //endregion

    override fun attachView(view: MainView?) {
        super.attachView(view)
        // Запуск постоянной проверки наличия сети
        restoreAppAfterNetworkOnline(view)
        coroutineScope.launch(Dispatchers.IO) {
            // Заходим в приложение, если в предыдущей сессии работы успешно авторизовались
            if (userChoose.getGithubUserModel().login.isNotEmpty()) {
                withContext(Dispatchers.Main) {
                    router.replaceScreen(appScreens.usersScreen())
                    (view as MainActivity).hideErrorMessage()
                }
            } else {
                val resultPingAuthorise: Boolean = pingResult(URL_AUTHORISE_TO_PING, false)
                val resultPingGithub: Boolean = pingResult(URL_GITHUB_TO_PING, true)
                // Переход на окно с авторизацией,
                // в случае наличия сети Интернет при старте приложения
                if ((networkStatus.isOnline()) &&
                    (resultPingAuthorise) && (resultPingGithub))
                    withContext(Dispatchers.Main) {
                        router.replaceScreen(appScreens.usersScreen())
                        (view as MainActivity).hideErrorMessage()
                    }
                // Вывод сообщения об отсутствии сети Интернет
                else if (!networkStatus.isOnline()) {
                    withContext(Dispatchers.Main) {
                        (view as MainActivity).showErrorMessage(MAIN_ERRORS.NO_INTERNET)
                    }
                }
                // Вывод сообщения об отсутствии доступа к серверу OAuth
                else if (!resultPingAuthorise) {
                    withContext(Dispatchers.Main) {
                        (view as MainActivity).showErrorMessage(MAIN_ERRORS.OAUTH_SERVER_ERROR)
                    }
                // Вывод сообщения об отсутствии доступа к серверу github.com
                } else if (!resultPingGithub) {
                    withContext(Dispatchers.Main) {
                        (view as MainActivity).
                            showErrorMessage(MAIN_ERRORS.GITHUB_SERVER_ERROR)
                    }
                }
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
    private fun restoreAppAfterNetworkOnline(view: MainView?) {
        var isOnline: Boolean = networkStatus.isOnline()
        var isGoToUsersScreen: Boolean = false
        coroutineScope.launch(Dispatchers.IO) {
            while(true) {
                if (networkStatus.isOnline()) {
                    val resultPingAuthorise: Boolean =
                        pingResult(URL_AUTHORISE_TO_PING, false)
                    val resultPingGithub: Boolean = pingResult(URL_GITHUB_TO_PING, true)
                    // Случай появления сети после её отключения
                    if ((!isOnline) && (resultPingAuthorise) && (resultPingGithub)) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                resourcesProvider.getContext(),
                                resourcesProvider.getString(R.string.github_yes_internet),
                                Toast.LENGTH_SHORT
                            ).show()
                            // Случай появления сети после её отключения,
                            // когда авторизация ещё не состоялась
                            if (userChoose.getGithubUserModel().login.isEmpty()) {
                                // Переход на окно с выбором пользователя (авторизацией пользователя)
                                router.replaceScreen(appScreens.usersScreen())
                                (view as MainActivity).hideErrorMessage()
                                isGoToUsersScreen = true
                            }
                            isOnline = true
                        }
                    // Случай появления сети после её отключения,
                    // когда авторизация ещё не состоялась
                    } else if ((!isGoToUsersScreen) &&
                        (userChoose.getGithubUserModel().login.isEmpty()) &&
                        (resultPingAuthorise) && (resultPingGithub))  {
                            withContext(Dispatchers.Main) {
                            // Переход на окно с выбором пользователя (авторизацией пользователя)
                            router.replaceScreen(appScreens.usersScreen())
                            (view as MainActivity).hideErrorMessage()
                            isOnline = true
                            isGoToUsersScreen = true
                        }
                    // Случай появления сети после её отключения,
                    // когда во время авторизации становится недоступен сервер OAuth
                    } else if ((userChoose.getGithubUserModel().login.isEmpty()) &&
                            (!resultPingAuthorise)) {
                        // Отображение сообщения с блокировкой экрана о том,
                        // что сервер для OAuth авторизации выключен
                        withContext(Dispatchers.Main) {
                            (view as MainActivity).
                                showErrorMessage(MAIN_ERRORS.OAUTH_SERVER_ERROR)
                            isGoToUsersScreen = false
                        }
                    // Случай появления сети после её отключения,
                    // когда во время авторизации становится недоступен сервер github.com
                    } else if ((userChoose.getGithubUserModel().login.isEmpty()) &&
                        (!resultPingGithub)) {
                        // Отображение сообщения с блокировкой экрана о том,
                        // что сервер github.com выключен
                        withContext(Dispatchers.Main) {
                            (view as MainActivity).
                                showErrorMessage(MAIN_ERRORS.GITHUB_SERVER_ERROR)
                            isGoToUsersScreen = false
                        }
                    // Случай, когда исчерпаны все разрешённые бесплатные запросы
                    } else if ((userChoose.getResponseCode() ==
                        ServerResponseStatusCode.CLIENT_ERROR) &&
                        (userChoose.getWaitingMinutes().first != 0L)) {
                        // Отображение сообщения о том, что все разрешённые запросы закончились
                        // и нужно подождать, чтобы появилась возможность
                        // отправлять новые запросы на github.com
                        withContext(Dispatchers.Main) {
                            (view as MainActivity).showErrorMessage(MAIN_ERRORS.CLIENT_ERROR)
                            isGoToUsersScreen = false
                        }
                    // Скрытие сообщения об ошибках
                    } else {
                        withContext(Dispatchers.Main) {
                            (view as MainActivity).hideErrorMessage()
                            // Если пользователь ещё не авторизовался,
                            // то переход на страницу с его авторизацией
                            if ((!isGoToUsersScreen) &&
                                (userChoose.getGithubUserModel().login.isEmpty()) &&
                                (resultPingAuthorise) && (resultPingGithub)) {
                                // Переход на окно с выбором пользователя (авторизацией пользователя)
                                router.replaceScreen(appScreens.usersScreen())
                                (view as MainActivity).hideErrorMessage()
                                isOnline = true
                                isGoToUsersScreen = true
                            }
                        }
                    }
                } else {
                    if (isOnline) {
                        if ((userChoose.getGithubUserModel().login.isNotEmpty())) {
                            // Уведомление пользователя о том, что сейчас сеть Интернет не доступна
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    resourcesProvider.getContext(),
                                    resourcesProvider.getString(R.string.github_no_internet),
                                    Toast.LENGTH_SHORT
                                ).show()
                                (view as MainActivity).hideErrorMessage()
                                isOnline = false
                            }
                        } else {
                            // Отображение сообщения с блокировкой экрана о том,
                            // что для OAuth авторизации нужен Интернет
                            withContext(Dispatchers.Main) {
                                (view as MainActivity).
                                showErrorMessage(MAIN_ERRORS.NO_INTERNET)
                                isOnline = false
                            }
                        }
                    }
                }
                delay(DELAY_TIME)
            }
        }
    }

    // Пингование сайта по его url
    fun pingResult(url: String, isHttps: Boolean): Boolean {
        try {
            var stringAnswer = ""
            val url = URL(url)
            if (isHttps) {
                // Открытие сессии HTTPS
                var urlConnection: HttpsURLConnection? = null
                urlConnection = url.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.readTimeout = READ_TIMEOUT
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
            } else {
                // Открытие сессии HTTP
                var urlConnection: HttpURLConnection? = null
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.readTimeout = READ_TIMEOUT
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
            }
        } catch (e: Exception) {
            Log.d("", e.localizedMessage)
            return false
        }
    }
}
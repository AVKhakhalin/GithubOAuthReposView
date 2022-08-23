package com.github.oauth.repositories.githuboauthreposview.view.main

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.oauth.repositories.githuboauthreposview.R
import com.github.oauth.repositories.githuboauthreposview.app.App
import com.github.oauth.repositories.githuboauthreposview.databinding.ActivityMainBinding
import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
import com.github.oauth.repositories.githuboauthreposview.remote.RetrofitService
import com.github.oauth.repositories.githuboauthreposview.utils.*
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
    // Db
    @Inject
    lateinit var db: AppDatabase
    // Retrofit
    @Inject
    lateinit var retrofitService: RetrofitService
    // Binding
    private lateinit var binding: ActivityMainBinding
    // userChoose
    private val userChoose: UserChooseRepository = App.instance.appComponent.userChoose()
    // Индикатор критических сообщений об ошибках
    private lateinit var errorMessage: TextView
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.injectMainActivity(this@MainActivity)
        // Подключение binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        /** Получение разрешений на запись информации */
        presenter.isStoragePermissionGranted(this@MainActivity)
        // Загрузка ранее сохранённых данных в SharedPreferences
        loadSavedData()
        // Инициализация индикатора сообщений о критических ошибках
        errorMessage = binding.criticalErrorMessage

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

        // Отображение содержимого окна
        setContentView(binding.root)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        // Сохранение данных о дате и количестве оставшихся запросов
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(
            SHARED_PREFERENCES_KEY, AppCompatActivity.MODE_PRIVATE)
        val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferencesEditor.putInt(SHARED_PREFERENCES_NUMBER_LIMIT_REQUESTS,
            userChoose.getNumberLimitRequest())
        sharedPreferencesEditor.putInt(SHARED_PREFERENCES_NUMBER_REMAINING_REQUESTS,
            userChoose.getNumberRemainingRequest())
        val requestsTimesList: List<Long> = userChoose.getActualRequestsTimesList()
        sharedPreferencesEditor.putInt(SHARED_PREFERENCES_REQUESTS_TIMES_NUMBER,
            requestsTimesList.size)
        userChoose.getActualRequestsTimesList().forEachIndexed { index, it ->
            sharedPreferencesEditor.putLong("$SHARED_PREFERENCES_REQUEST_TIME$index", it)
        }
        sharedPreferencesEditor.apply()
        // Удаление навигатора
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

    // Отобразить сообщение об ошибке, если выключен сервер для OAuth авторизации
    @SuppressLint("SetTextI18n")
    fun showErrorMessage(mainError: MAIN_ERRORS) {
        when(mainError) {
            MAIN_ERRORS.OAUTH_SERVER_ERROR -> {
                errorMessage.text = resources.getString(R.string.no_oauth_server_error_message_text)
            }
            MAIN_ERRORS.NO_INTERNET -> {
                errorMessage.text = resources.getString(R.string.no_internet_error_message_text)
            }
            MAIN_ERRORS.GITHUB_SERVER_ERROR -> {
                errorMessage.text = resources.getString(R.string.no_github_server_error_message_text)
            }
            MAIN_ERRORS.CLIENT_ERROR -> {
                errorMessage.text =
                    "${resources.getString(R.string.client_error_message_text_first)}${
                    userChoose.getNumberLimitRequest()} ${resources.getString(
                    R.string.client_error_message_text_middle)} ${
                    userChoose.getWaitingTime().first}${resources.getString(
                        R.string.client_error_message_text_last)} ${
                        userChoose.getWaitingTime().second}${resources.getString(
                        R.string.client_error_message_text_last_one)}"
            }
        }
        binding.criticalErrorMessageContainer.visibility = View.VISIBLE
        binding.loadingText.visibility = View.INVISIBLE
        binding.loadingView.visibility = View.INVISIBLE
    }
    // Спрятать сообщение об ошибке, если включен сервер для OAuth авторизации
    fun hideErrorMessage() {
        binding.criticalErrorMessageContainer.visibility = View.GONE
        binding.loadingText.visibility = View.GONE
        binding.loadingView.visibility = View.GONE
    }

    // Загрузка ранее сохранённых данных в SharedPreferences
    private fun loadSavedData() {
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences(SHARED_PREFERENCES_KEY, AppCompatActivity.MODE_PRIVATE)
        userChoose.setGithubUserModel(GithubUserModel("",
            sharedPreferences.getString(SHARED_PREFERENCES_USER_LOGIN, "").toString(),
            "", ""))
        userChoose.setNumberLimitRequest(sharedPreferences.
            getInt(SHARED_PREFERENCES_NUMBER_LIMIT_REQUESTS, -1))
        userChoose.setNumberRemainingRequest(sharedPreferences.
            getInt(SHARED_PREFERENCES_NUMBER_REMAINING_REQUESTS, -1))
        val requestsTimesNumber: Int = sharedPreferences.
            getInt(SHARED_PREFERENCES_REQUESTS_TIMES_NUMBER, 0)
        repeat(requestsTimesNumber) { index ->
            userChoose.setRequestTime(sharedPreferences.
                getLong("$SHARED_PREFERENCES_REQUEST_TIME$index", 0))
        }
    }
}
package com.github.oauth.repositories.githuboauthreposview.view.users

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.oauth.repositories.githuboauthreposview.R
import com.github.oauth.repositories.githuboauthreposview.app.App
import com.github.oauth.repositories.githuboauthreposview.databinding.FragmentUsersBinding
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
import com.github.oauth.repositories.githuboauthreposview.utils.*
import com.github.oauth.repositories.githuboauthreposview.utils.binding.viewBinding
import com.github.oauth.repositories.githuboauthreposview.view.base.BackButtonListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class UsersFragment: MvpAppCompatFragment(R.layout.fragment_users), UsersView, BackButtonListener {
    /** Задание переменных */ //region
    // presenter
    private val presenter by moxyPresenter {
        App.instance.initGithubUsersSubcomponent()
        App.instance.usersSubcomponent?.provideUsersPresenter()!!
    }
    // binding
    private val binding by viewBinding<FragmentUsersBinding>()
    // Кнопки
    lateinit var reloadOAuthButton: Button
    lateinit var loginButton: Button
    lateinit var logoutButton: Button
    lateinit var viewRepositoriesButton: Button
    // WebView
    lateinit var webView: WebView
    // userChoose
    private val userChoose: UserChooseRepository = App.instance.appComponent.userChoose()
    // CurrentUserLogin
    private var userLogin: String = userChoose.getGithubUserModel().login
    //endregion

    companion object {
        fun newInstance() = UsersFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получение обновлённых данных о пользователе
        getAndSaveUserData(userLogin)
        // Инициализация WebView и кнопок
        initWebViewAndButtons()
    }

    @SuppressLint("SetTextI18n")
    private fun initWebViewAndButtons() {
        webView = binding.webView
        webView.webViewClient = object: WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            @SuppressLint("SetTextI18n")
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                request?.let { it ->
                    val requestUrl: String = it.url.toString()
                    if ((requestUrl.length > TARGET_USER_NAME_URL.length) &&
                            (requestUrl.substring(0, TARGET_USER_NAME_URL.length) ==
                            TARGET_USER_NAME_URL) &&
                        (requestUrl.substring(TARGET_USER_NAME_URL.length) != EMPTY_NAME_MASK) &&
                        (requestUrl.indexOf("?") == -1) &&
                        (requestUrl.indexOf("=") == -1)) {
                        userLogin = requestUrl.substring(TARGET_USER_NAME_URL.length)
                        loginButton.visibility = View.INVISIBLE
                        logoutButton.visibility = View.VISIBLE
                        reloadOAuthButton.visibility = View.GONE
                        view?.visibility = View.INVISIBLE
                        viewRepositoriesButton.text = "${requireContext().getString(
                            R.string.github_review_text_start)}$userLogin ${
                                requireContext().getString(R.string.github_review_text_end)}"
                        saveToSharedPreferencesUserLogin(userLogin)
                        getAndSaveUserData(userLogin)
                    } else if ((requestUrl == LOGOUT_MASK_ONE) ||
                        (requestUrl == LOGOUT_MASK_TWO)) {
                        loginButton.visibility = View.VISIBLE
                        logoutButton.visibility = View.INVISIBLE
                        reloadOAuthButton.visibility = View.GONE
                        viewRepositoriesButton.visibility = View.INVISIBLE
                        view?.visibility = View.INVISIBLE
                        viewRepositoriesButton.text =
                            requireContext().getString(R.string.github_review_text)
                        userLogin = ""
                        saveToSharedPreferencesUserLogin("")
                        getAndSaveUserData("")
                    }
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        reloadOAuthButton = binding.reloadOauthButton.also {
            it.setOnClickListener {
                presenter.reloadOAuth()
            }
        }
        logoutButton = binding.githubLogoutBtn.also {
            if (userLogin.isNotEmpty()) {
                it.visibility = View.VISIBLE
            }
            it.setOnClickListener {
                loginButton.visibility = View.INVISIBLE
                logoutButton.visibility = View.INVISIBLE
                viewRepositoriesButton.visibility = View.INVISIBLE
                webView.loadUrl(LOGOUT_GITHUB)
                webView.visibility = View.VISIBLE
                reloadOAuthButton.visibility = View.VISIBLE
            }
        }
        viewRepositoriesButton = binding.githubReviewBtn.also {
            if (userLogin.isNotEmpty()) {
                it.text = "${requireContext().getString(
                    R.string.github_review_text_start)}$userLogin ${
                    requireContext().getString(R.string.github_review_text_end)}"
            } else {
                it.visibility = View.INVISIBLE
            }
            it.setOnClickListener {
                presenter.onRepoClicked()
            }
        }
        loginButton = binding.githubLoginBtn.also {
            if (userLogin.isNotEmpty()) {
                it.visibility = View.INVISIBLE
            }
            it.setOnClickListener {
                loginButton.visibility = View.INVISIBLE
                logoutButton.visibility = View.INVISIBLE
                viewRepositoriesButton.visibility = View.INVISIBLE
                webView.loadUrl(AUTHORISE_URL)
                webView.visibility = View.VISIBLE
                reloadOAuthButton.visibility = View.VISIBLE
            }
        }
    }

    override fun backPressed(): Boolean {
        presenter.backPressed()
        return true
    }

    private fun saveToSharedPreferencesUserLogin(userLogin: String) {
        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences(
                SHARED_PREFERENCES_KEY, AppCompatActivity.MODE_PRIVATE)
        val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferencesEditor.putString(SHARED_PREFERENCES_USER_LOGIN, userLogin)
        sharedPreferencesEditor.apply()
    }

    // Получение обновлённых данных о пользователе
    override fun getAndSaveUserData(userLogin: String) {
        presenter.getGithubUserInfo(userLogin)
    }

    override fun logoutToGithub() {
//        TODO("Not yet implemented")
    }

    override fun moveToRepositories() {
        presenter.onRepoClicked()
    }

    override fun showLoading() {
        binding.loadingView.visibility = View.VISIBLE
        viewRepositoriesButton.visibility = View.INVISIBLE
    }

    override fun hideLoading() {
        binding.loadingView.visibility = View.INVISIBLE
        viewRepositoriesButton.visibility = View.VISIBLE
    }
}
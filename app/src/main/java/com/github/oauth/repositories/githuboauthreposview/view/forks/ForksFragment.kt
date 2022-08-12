package com.github.oauth.repositories.githuboauthreposview.view.forks

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.oauth.repositories.githuboauthreposview.R
import com.github.oauth.repositories.githuboauthreposview.app.App
import com.github.oauth.repositories.githuboauthreposview.databinding.FragmentForksBinding
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
import com.github.oauth.repositories.githuboauthreposview.utils.binding.viewBinding
import com.github.oauth.repositories.githuboauthreposview.view.base.BackButtonListener
import com.github.oauth.repositories.githuboauthreposview.view.forks.adapter.ForksAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class ForksFragment : MvpAppCompatFragment(R.layout.fragment_forks), ForksView, BackButtonListener {
    /** ЗАДАНИЕ ПЕРЕМЕННЫХ */ //region
    // binding
    private val binding by viewBinding<FragmentForksBinding>()
    // presenter
    private val presenter by moxyPresenter {
        App.instance.initForksSubcomponent()
        App.instance.forksSubcomponent?.provideForksPresenter()!!
    }
    // userChoose
    private val userChoose: UserChooseRepository = App.instance.appComponent.userChoose()
    // adapter
    private val adapter by lazy {
        ForksAdapter { presenter.onRepoClicked(it) }
    }
    //endregion

    companion object {
        fun newInstance() = ForksFragment()
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Отображение общей информации по репозиторию
        showGeneralRepoInfo()

        /** Установка списка репозиториев пользователя */
        binding.forksCommitsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.forksCommitsRecycler.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        // Отображение общей информации по репозиторию
        showGeneralRepoInfo()
    }

    override fun backPressed(): Boolean {
        presenter?.let { presenter ->
            presenter.backPressed()
        }
        return true
    }

    // Отображение общей информации по репозиторию
    private fun showGeneralRepoInfo() {
        binding.repoTitle.text = userChoose.getGithubRepoModel().name
        binding.repoDescription.text = userChoose.getGithubRepoModel().description
        binding.repoAuthorName.text = userChoose.getGithubRepoModel().owner.login
        binding.repoForksNumber.text = userChoose.getGithubRepoModel().forksCount.toString()
        binding.forksWatches.text = userChoose.getGithubRepoModel().watchers_count.toString()
    }
}
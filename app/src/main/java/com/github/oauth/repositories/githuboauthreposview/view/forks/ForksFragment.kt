package com.github.oauth.repositories.githuboauthreposview.view.forks

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.oauth.repositories.githuboauthreposview.R
import com.github.oauth.repositories.githuboauthreposview.app.App
import com.github.oauth.repositories.githuboauthreposview.databinding.FragmentForksBinding
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import com.github.oauth.repositories.githuboauthreposview.utils.binding.viewBinding
import com.github.oauth.repositories.githuboauthreposview.view.base.BackButtonListener
import com.github.oauth.repositories.githuboauthreposview.view.forks.adapter.ForksAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class ForksFragment: MvpAppCompatFragment(R.layout.fragment_forks), ForksView, BackButtonListener {
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
        ForksAdapter { presenter.onCommitClicked(it) }
    }
    // Инстанс фрагмента
    companion object {
        fun newInstance() = ForksFragment()
    }
    //endregion


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Отображение общей информации по репозиторию
        showGeneralRepoInfo()
        // Отображение индикатора загрузки данных о коммитах
        showLoading()
        // Установка списка коммитов в репозитории
        setCommitsList()
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        // Отображение общей информации по репозиторию
        showGeneralRepoInfo()
        // Установка списка коммитов в репозитории
        setCommitsList()
    }

    override fun backPressed(): Boolean {
        presenter.backPressed()
        return true
    }

    // Отображение общей информации по репозиторию
    @SuppressLint("SetTextI18n")
    private fun showGeneralRepoInfo() {
        binding.repoTitle.text = "${requireContext().getString(R.string.repo_title_tools_text)}: ${
            userChoose.getGithubRepoModel().name}"
        if (userChoose.getGithubRepoModel().description.isNotEmpty())
            binding.repoDescription.text = "${requireContext().getString(
            R.string.repo_description_tools_text)}: ${userChoose.getGithubRepoModel().description}"
        else
            binding.repoDescription.text =
                requireContext().getString(R.string.no_repo_description_text)
        binding.repoAuthorName.text = "${requireContext().getString(
            R.string.repo_author_name_tools_text)}: ${userChoose.getGithubRepoModel().owner.login}"
        binding.repoForksNumber.text = "${requireContext().getString(
            R.string.repo_forks_number_tools_text)}: ${userChoose.getGithubRepoModel().forksCount}"
        binding.forksWatches.text = "${requireContext().getString(
            R.string.forks_watches_tools_text)}: ${userChoose.getGithubRepoModel().watchers_count}"
        presenter.loadAvatar(userChoose.getGithubUserModel().avatarUrl, binding.repoUserAvatar)
    }

    // Установка списка коммитов в репозитории
    private fun setCommitsList() {
        binding.forksCommitsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.forksCommitsRecycler.adapter = adapter
    }

    override fun showLoading() {
        binding.loadingView.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.loadingView.visibility = View.INVISIBLE
    }

    override fun showCommits(commits: List<GithubCommitModel>) {
        hideLoading()
        adapter.submitList(commits)
    }
}
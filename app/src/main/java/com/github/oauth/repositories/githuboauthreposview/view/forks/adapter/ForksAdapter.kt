package com.github.oauth.repositories.githuboauthreposview.view.forks.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.oauth.repositories.githuboauthreposview.databinding.ItemCommitBinding
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel

class ForksAdapter(
    private val itemClickListener: (GithubCommitModel) -> Unit,
): ListAdapter<GithubCommitModel, ForksAdapter.ForksViewHolder>(GithubForksItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForksViewHolder {
        return ForksViewHolder(
            ItemCommitBinding.inflate(LayoutInflater.from(parent.context), parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: ForksViewHolder, position: Int) {
        holder.showCommit(currentList[position])
    }

    inner class ForksViewHolder(private val vb: ItemCommitBinding):
        RecyclerView.ViewHolder(vb.root) {

        @SuppressLint("SetTextI18n")
        fun showCommit(commit: GithubCommitModel) {
            vb.itemCommitContainer.setOnClickListener { itemClickListener(commit) }
            vb.commitDate.text = commit.commit.author.date
            vb.commitHash.text = commit.sha
            vb.commitMessage.text = "${commit.commit.author.name}: ${commit.commit.message}"
        }
    }
}

object GithubForksItemCallback: DiffUtil.ItemCallback<GithubCommitModel>() {

    override fun areItemsTheSame(oldItem: GithubCommitModel, newItem: GithubCommitModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: GithubCommitModel, newItem: GithubCommitModel
    ): Boolean {
        return oldItem == newItem
    }
}
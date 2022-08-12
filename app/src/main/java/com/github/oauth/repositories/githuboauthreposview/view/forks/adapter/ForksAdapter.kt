package com.github.oauth.repositories.githuboauthreposview.view.forks.adapter

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

        fun showCommit(commit: GithubCommitModel) {
            vb.root.setOnClickListener { itemClickListener(commit) }
            vb.commitDate.text = commit.owner.author.date
            vb.commitAuthorName.text = commit.owner.author.name
            vb.commitHash.text = commit.sha
            vb.commitMessage.text = commit.owner.message
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
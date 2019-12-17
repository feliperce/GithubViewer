package com.example.githubviewer.ui.rep.view

import androidx.recyclerview.widget.DiffUtil
import com.example.githubviewer.data.model.Repo

class DiffUtilsCallback : DiffUtil.ItemCallback<Repo>() {

    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem.id == newItem.id
                && oldItem.gitUrl == newItem.gitUrl
                && oldItem.url == newItem.url
    }
}
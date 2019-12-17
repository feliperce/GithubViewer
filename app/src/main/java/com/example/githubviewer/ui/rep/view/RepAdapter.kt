package com.example.githubviewer.ui.rep.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubviewer.R
import com.example.githubviewer.data.model.Repo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_rep_list.view.*
import kotlinx.android.synthetic.main.item_rep_list.view.descriptionTextView
import kotlinx.android.synthetic.main.item_rep_list.view.rootLayout
import kotlinx.android.synthetic.main.item_rep_list.view.titleTextView
import kotlinx.android.synthetic.main.item_rep_list.view.userImageView
import kotlinx.android.synthetic.main.item_rep_list.view.userNameTextView

class RepAdapter(
    private val listenerLiveData: MutableLiveData<RepCommand>
) : PagedListAdapter<Repo, RepAdapter.RepViewHolder>(
    DiffUtilsCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepAdapter.RepViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rep_list, parent, false)
        return RepViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepAdapter.RepViewHolder, position: Int) {
        getItem(position)?.let { rep ->
            holder.apply {
                titleTextView.text = rep.name
                descriptionTextView.text = rep.description
                forkTextView.text = rep.forks.toString()
                starTextView.text = rep.stargazersCount.toString()
                userNameTextView.text = rep.owner.login
                Picasso.get().load(rep.owner.avatarUrl).into(userImageView)

                rootLayout.setOnClickListener {
                    listenerLiveData.postValue(RepCommand.RepClick(rep.owner.login, rep.name))
                }
            }
        }
    }

    class RepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rootLayout: ConstraintLayout = itemView.rootLayout
        var titleTextView: TextView = itemView.titleTextView
        var descriptionTextView: TextView = itemView.descriptionTextView
        var forkTextView: TextView = itemView.forkTextView
        var starTextView: TextView = itemView.startTextView
        var userNameTextView: TextView = itemView.userNameTextView
        var userImageView: ImageView = itemView.userImageView
    }

    sealed class RepCommand {
        class RepClick(val user: String, val rep: String) : RepCommand()
    }
}
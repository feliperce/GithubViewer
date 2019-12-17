package com.example.githubviewer.ui.reppr.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.githubviewer.R
import com.example.githubviewer.data.remote.response.RepPrResponse
import com.example.githubviewer.extension.iso9801ToDateFormattedString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_rep_pr_list.view.*

class RepPrAdapter(
    private val listenerLiveData: MutableLiveData<RepPrCommand>,
    private val repPrResponse: List<RepPrResponse>) : RecyclerView.Adapter<RepPrAdapter.RepViewHolder>() {

    override fun getItemCount(): Int {
        return repPrResponse.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepPrAdapter.RepViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rep_pr_list, parent, false)
        return RepViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepPrAdapter.RepViewHolder, position: Int) {
        repPrResponse[position].let { pr ->
            holder.apply {
                titleTextView.text = pr.title
                descriptionTextView.text = pr.body
                userNameTextView.text = pr.user.login
                Picasso.get().load(pr.user.avatarUrl).into(userImageView)
                rootLayout.setOnClickListener { v ->
                    listenerLiveData.postValue(RepPrCommand.PrClick(pr.htmlUrl))
                }
                dateTextView.text = pr.createdAt.iso9801ToDateFormattedString()
            }
        }
    }

    class RepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rootLayout: ConstraintLayout = itemView.rootLayout
        var titleTextView: TextView = itemView.titleTextView
        var descriptionTextView: TextView = itemView.descriptionTextView
        var userNameTextView: TextView = itemView.userNameTextView
        var userImageView: ImageView = itemView.userImageView
        var dateTextView: TextView = itemView.dateTextView
    }

    sealed class RepPrCommand {
        class PrClick(val url: String) : RepPrCommand()
    }
}
package com.example.gitapi.activity.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gitapi.R
import com.example.gitapi.model.GitHubRepo

class ReposAdapter(repos: List<GitHubRepo>, rowLayout: Int, context: Context) :
    RecyclerView.Adapter<ReposAdapter.ReposViewHolder>() {
    var repos: List<GitHubRepo>? = null
    var rowLayout: Int = 0
    var context: Context? = null

    init {
        this.repos = repos
        this.rowLayout = rowLayout
        this.context = context
    }

    class ReposViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var reposLayout: LinearLayout
        internal var repoName: TextView
        internal var repoDescription: TextView
        internal var repolanguage: TextView


        init {
            reposLayout = v.findViewById<View>(R.id.repo_item_layout) as LinearLayout
            repoName = v.findViewById<View>(R.id.repoName) as TextView
            repoDescription = v.findViewById<View>(R.id.repoDescription) as TextView
            repolanguage = v.findViewById<View>(R.id.repoLanguage) as TextView
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReposViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
        return ReposViewHolder(view)
    }


    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.repoName.text = repos!![position].name
        holder.repoDescription.text = repos!![position].description
        holder.repolanguage.text = repos!![position].language
    }

    override fun getItemCount(): Int {
        return repos!!.size
    }
}



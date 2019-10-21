package com.example.gitapi.activity.Repositories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.gitapi.model.GitHubRepo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitapi.R
import com.example.gitapi.activity.Adapters.ReposAdapter
import com.example.gitapi.rest.ApiClients
import com.example.gitapi.rest.GitHubRepoEndPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersRepositories : AppCompatActivity() {

    lateinit var receivedUserName: String
    lateinit var userNameTV: TextView
    lateinit var mRecyclerView: RecyclerView
    var myDataSource: MutableList<GitHubRepo> = ArrayList()
    internal var myAdapter: RecyclerView.Adapter<*>? = null
    private lateinit var mgetRepoViewModel: UsersRepositoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(com.example.gitapi.R.layout.activity_repositiories)

        val extras = intent.extras
        receivedUserName = extras!!.getString("username").toString()


        myDataSource = ArrayList()

        userNameTV = findViewById(com.example.gitapi.R.id.userNameTV);
        userNameTV.text = "User: $receivedUserName"
        mRecyclerView = findViewById(com.example.gitapi.R.id.repos_recycler_view)
        mRecyclerView.setLayoutManager(LinearLayoutManager(this));
        myAdapter = ReposAdapter(
            myDataSource, R.layout.listitems,
            getApplicationContext()
        )
        mRecyclerView.adapter = myAdapter
        mgetRepoViewModel = ViewModelProviders.of(this, UsersRepositoryFactory(application, receivedUserName))
            .get(UsersRepositoryViewModel::class.java)
        mgetRepoViewModel.init()
        mgetRepoViewModel.allRepositories?.observe(this, Observer { repos ->
            myDataSource.clear()
            myDataSource.addAll(repos)
            myAdapter!!.notifyDataSetChanged()
            //repos
        })


    }
}



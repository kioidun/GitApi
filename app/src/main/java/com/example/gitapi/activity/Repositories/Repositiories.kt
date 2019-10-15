package com.example.gitapi.activity.Repositories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.gitapi.model.GitHubRepo
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitapi.R
import com.example.gitapi.activity.Adapters.ReposAdapter
import com.example.gitapi.rest.ApiClients
import com.example.gitapi.rest.GitHubRepoEndPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repositiories : AppCompatActivity() {

    lateinit var receivedUserName: String
    lateinit var userNameTV: TextView
    lateinit var mRecyclerView: RecyclerView
    var myDataSource: MutableList<GitHubRepo> = ArrayList()
    internal var myAdapter: RecyclerView.Adapter<*>? = null

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

        loadRepositories()
    }

    private fun loadRepositories() {
        val apiService = ApiClients.client!!.create(GitHubRepoEndPoint::class.java)
        val call = apiService.getUser(receivedUserName)
        call.enqueue(object : Callback<List<GitHubRepo>> {
            override fun onResponse(call: Call<List<GitHubRepo>>, response: Response<List<GitHubRepo>>) {
                response.body()
                 myDataSource.clear()
                 myDataSource.addAll(response.body()!!)
                 myAdapter!!.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<GitHubRepo>>, t: Throwable) {
                Log.e("Repos", t.toString());
            }
        })
    }
}



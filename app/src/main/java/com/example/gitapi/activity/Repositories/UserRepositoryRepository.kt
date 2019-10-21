package com.example.gitapi.activity.Repositories

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gitapi.activity.UserActivity.UserActivityViewModel
import com.example.gitapi.model.GitHubRepo
import com.example.gitapi.rest.ApiClients
import com.example.gitapi.rest.GitHubRepoEndPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryRepository() {
    private var instance:UserRepositoryRepository ? = null
    private  var getRepoData: MutableLiveData<ArrayList<GitHubRepo>>? = null

    private lateinit var mApplication: Application
    private var receivedUserName: String = ""
    var myDataSource  = ArrayList<GitHubRepo>()
    private lateinit var gitHubRepo: GitHubRepo



    constructor(
        mApplication: Application,
        newString: String
    ) : this() {
        this.mApplication = mApplication
        this.receivedUserName = newString
    }
    fun getInstance():UserRepositoryRepository {
        if(instance == null){
            instance = UserRepositoryRepository()
        }
        return instance as UserRepositoryRepository
    }
    fun setname(name: String) {
        this.receivedUserName = name
    }

    val getRepositories: MutableLiveData<ArrayList<GitHubRepo>>?
    get(){
         getRepoData = MutableLiveData()
        loadRepositories()
        return getRepoData
    }

    private fun loadRepositories() {
        val apiService = ApiClients.client!!.create(GitHubRepoEndPoint::class.java)
        val call = apiService.getUser(receivedUserName)
        call.enqueue(object : Callback<List<GitHubRepo>> {
            override fun onResponse(call: Call<List<GitHubRepo>>, response: Response<List<GitHubRepo>>) {
                response.body()
                myDataSource.clear()
//                gitHubRepo = GitHubRepo(
//                    response.body().toString(),
//                    response.body().toString(),
//                    response.body().toString()
//
//                )
                response.body()?.let { myDataSource.addAll(it) }
                getRepoData?.postValue(myDataSource)
            }

            override fun onFailure(call: Call<List<GitHubRepo>>, t: Throwable) {
                Log.e("Repos", t.toString())
            }
        })
    }
}
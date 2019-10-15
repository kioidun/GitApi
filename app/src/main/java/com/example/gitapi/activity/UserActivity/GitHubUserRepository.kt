package com.example.gitapi.activity.UserActivity

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.gitapi.model.AcessToken
import com.example.gitapi.rest.ApiClients
import com.example.gitapi.rest.GitApiClient
import com.example.gitapi.rest.GitHubUserEndPoints
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GitHubUserRepository() {
    private var instance: GitHubUserRepository? = null
    var dataset: ArrayList<GitHubUser>? = null
    private lateinit var mApplication: Application
    private var newString: String = ""
    private lateinit var gitHubUser: GitHubUser
    val deliveryItemList = ArrayList<GitHubUser>()
    private var clientId: String = ""
    private var clientSecret: String = ""
    private var code: String = ""


    constructor(
        mApplication: Application,
        newString: String,
        clientSecret: String,
        clientId: String,
        code: String
    ) : this() {
        this.mApplication = mApplication
        this.newString = newString
        this.clientSecret = clientSecret
        this.clientId = clientId
        this.code = code
    }

    fun setname(name: String) {
        this.newString = name
    }

    fun setapprovals(clientId: String, clientSecret: String, code: String) {
        this.clientId = clientId
        this.clientSecret = clientSecret
        this.code = code
    }

    fun getInstance(): GitHubUserRepository {
        if (instance == null) {
            instance = GitHubUserRepository()
        }
        return instance as GitHubUserRepository
    }

    val nice: MutableLiveData<ArrayList<GitHubUser>>
        get() {
            approval()
            setNames()
            val data = MutableLiveData<ArrayList<GitHubUser>>()

            data.value = dataset
            return data
        }

    fun approval() {
        val apiService = GitApiClient.client!!.create(GitHubUserEndPoints::class.java)

        val call = apiService.getAccessToken(clientId, clientSecret, code)

        call.enqueue(object : Callback<AcessToken> {
            override fun onFailure(call: Call<AcessToken>, t: Throwable) {

            }

            override fun onResponse(call: Call<AcessToken>, response: Response<AcessToken>) {
            }
        })
    }

    fun setNames() {
        val apiService = ApiClients.client!!.create(GitHubUserEndPoints::class.java)

        val call = apiService.getUser(newString)
        call.enqueue(object : Callback<GitHubUser> {
            override fun onResponse(call: Call<GitHubUser>, response: Response<GitHubUser>) {
                response.body()
                gitHubUser = GitHubUser(
                    response.body()?.login.toString(),
                    response.body()?.name,
                    response.body()?.followers.toString(),
                    response.body()?.following.toString(),
                    response.body()?.email,
                    response.body()?.avatar.toString()
                )
                deliveryItemList.addAll(listOf(gitHubUser))
                nice.postValue(deliveryItemList)
            }

            override fun onFailure(call: Call<GitHubUser>, t: Throwable) {
            }
        })
    }
}
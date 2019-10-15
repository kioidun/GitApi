package com.example.gitapi.activity.UserActivity

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserActivityViewModel() : ViewModel() {
    val deliveryItemList = ArrayList<GitHubUser>()
    private lateinit var gitHubUser: GitHubUser
    private var allmitable: MutableLiveData<ArrayList<GitHubUser>>? = null
    lateinit var mRepo: GitHubUserRepository
    private lateinit var mApplication: Application
    private var newString: String = ""
    private var clientId: String = ""
    private var clientSecret: String = ""
    private var code: String = ""

    constructor(
        mApplication: Application,
        newString: String,
        clientId: String,
        clientSecret: String,
        code: String
    ) : this() {
        this.mApplication = mApplication
        this.newString = newString
        this.clientId = clientId
        this.clientSecret = clientSecret
        this.code = code
    }

    fun init() {
        if (allmitable != null) {
            return
        }
        mRepo = GitHubUserRepository().getInstance()
        mRepo.setapprovals(clientId, clientSecret, code)
        mRepo.setname(newString)
        allmitable = mRepo.nice
    }

    val allDeliveryItems: LiveData<ArrayList<GitHubUser>>
        get() {
            return allmitable!!
        }
}
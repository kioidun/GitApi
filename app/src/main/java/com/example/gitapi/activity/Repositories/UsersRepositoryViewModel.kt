package com.example.gitapi.activity.Repositories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitapi.activity.UserActivity.GitHubUserRepository
import com.example.gitapi.model.GitHubRepo

class UsersRepositoryViewModel() : ViewModel() {
    private var getRepository:MutableLiveData<ArrayList<GitHubRepo>>? = null
    lateinit var mRepo:UserRepositoryRepository
    private lateinit var mApplication: Application
    private var receivedUserName: String = ""

    constructor(
        mApplication: Application,
        receivedUserName: String
    ) : this() {
        this.mApplication = mApplication
        this.receivedUserName = receivedUserName
    }

    fun init(){
        if (getRepository != null){
            return
        }
        mRepo = UserRepositoryRepository().getInstance()
        mRepo.setname(receivedUserName)
        getRepository = mRepo.getRepositories

    }

    val allRepositories: LiveData<ArrayList<GitHubRepo>>?
    get() {

        return getRepository
    }
}
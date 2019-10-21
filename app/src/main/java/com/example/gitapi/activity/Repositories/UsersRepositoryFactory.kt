package com.example.gitapi.activity.Repositories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitapi.activity.UserActivity.UserActivityViewModel

class UsersRepositoryFactory(application: Application,
                             newString: String
) : ViewModelProvider.Factory {
    private lateinit var receivedUserName: String
    private var mApplication: Application

    init {
        mApplication = application
        this.receivedUserName = newString

    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UsersRepositoryViewModel(mApplication, receivedUserName) as T
    }
}
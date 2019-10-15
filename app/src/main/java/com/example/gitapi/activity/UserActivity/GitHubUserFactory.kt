package com.example.gitapi.activity.UserActivity

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GitHubUserFactory(
    application: Application,
    newString: String,
    clientSecret: String,
    clientId: String,
    code: String
) : ViewModelProvider.Factory {
    private lateinit var newString: String
    private lateinit var clientId: String
    private lateinit var clientSecret: String
    private var mApplication: Application
    private lateinit var code: String

    init {
        mApplication = application
        this.newString = newString
        this.clientId = clientId
        this.clientSecret = clientSecret
        this.code = code

    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserActivityViewModel(mApplication, newString, clientId, clientSecret, code) as T
    }
}

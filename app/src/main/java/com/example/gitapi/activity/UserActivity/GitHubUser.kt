package com.example.gitapi.activity.UserActivity

import com.google.gson.annotations.SerializedName

class GitHubUser(login: String, name: String?, followers: String, following: String, avatar: String?, email: String) {
    @SerializedName("login")
    var login: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("followers")
    var followers: String? = null

    @SerializedName("following")
    var following: String? = null

    @SerializedName("avatar_url")
    var avatar: String? = null

    @SerializedName("email")
    var email: String? = null

    init {
        this.email = email
        this.avatar = avatar
        this.following = following
        this.followers = followers
        this.name = name
        this.login = login
    }

}
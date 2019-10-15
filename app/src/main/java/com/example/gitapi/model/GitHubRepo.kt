package com.example.gitapi.model

import com.google.gson.annotations.SerializedName

class GitHubRepo(language: String, description: String, name: String) {
    @SerializedName("name")
    var name: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("language")
    var language: String? = null

    init {

        this.language = language
        this.description = description
        this.name = name
    }
}



package com.example.gitapi.rest

import com.example.gitapi.model.GitHubRepo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubRepoEndPoint {
    @GET("/users/{user}/repos")
    fun getUser(@Path("user") user: String): Call<List<GitHubRepo>>
}
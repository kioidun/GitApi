package com.example.gitapi.rest

import com.example.gitapi.activity.UserActivity.GitHubUser
import com.example.gitapi.model.AcessToken
import retrofit2.Call
import retrofit2.http.*

interface GitHubUserEndPoints {

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String
    ): Call<AcessToken>


    @GET("/users/{user}")
    fun getUser(@Path("user") user: String): Call<GitHubUser>
}

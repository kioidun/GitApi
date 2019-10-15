package com.example.gitapi.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GitApiClient {

    val BASE_URL = "https://github.com/"
    private var retrofit: Retrofit? = null


    // for static you use a variable
    val client: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}

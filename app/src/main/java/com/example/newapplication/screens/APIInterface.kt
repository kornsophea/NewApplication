package com.example.newapplication.screens

import com.example.newapplication.DataModel.User
import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET("user")
    fun getUser():Call<List<User>>
}
package com.itsrdb.lcchallenger

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//https://leetcode-api-1d31.herokuapp.com/api/user/itsrdb/submissions
const val baseUrl = "https://leetcode-api-1d31.herokuapp.com/api/"

interface APIInterface {

    @GET("user/{user}/submissions")
    fun getSubmissions(@Path("user") user : String?) : Call<OuterSubmissions>

    @GET("user/{user}/recent/submissions")
    fun getRecentSubmissions(@Path("user") user: String?) : Call<OuterRecentSubmissions>
}

object APIService{
    val apiInstance : APIInterface
    init {
        val retro = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiInstance = retro.create(APIInterface::class.java)
    }
}
package com.example.smartgamesmobile.api



import com.example.smart.model.Games
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductCalls {


    @GET("/games")
    fun getGames(): Call<List<Games>>


}



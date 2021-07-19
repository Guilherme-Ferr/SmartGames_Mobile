package com.example.smartgamesmobile.api

import com.example.smart.model.Games
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GameCalls {
    @GET("/games")
    fun getGames(): Call<List<Games>>

    @GET("/games/{id}")
    fun getOneGame(@Path("id") id: Int): Call<Games>
}
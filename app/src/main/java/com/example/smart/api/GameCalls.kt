package com.example.smartgamesmobile.api

import com.example.smart.model.Game
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface GameCalls {
    @GET("/games")
    fun getGames(): Call<List<Game>>

    @GET("/games/{id}")
    fun getOneGame(@Path("id") id: Int): Call<Game>

    @PUT("/games/{id}")
    fun updateGameDiscount(@Path("id") id: Int): Call<Game>

}
package com.example.smart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flowtrandingsystem.gui.api.RetrofitApi
import com.example.smart.adapter.GamesAdapter
import com.example.smart.adapter.PlatformsAdapter
import com.example.smart.model.Games
import com.example.smartgamesmobile.api.GameCalls
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var rvGames: RecyclerView
    lateinit var rvPlatforms: RecyclerView
    lateinit var adapterGames: GamesAdapter
    lateinit var adapterPlatforms: PlatformsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        supportActionBar?.setTitle("SMART GAMES")

        rvGames = findViewById(R.id.recycler_view_games)
        rvGames.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapterGames = GamesAdapter(this)

        rvGames.adapter = adapterGames

        loadGamesList()
    }

    private fun loadGamesList() {

        var gameList: List<Games>
        val retrofit = RetrofitApi.getRetrofit()
        val gamesCall = retrofit.create(GameCalls::class.java)
        val call = gamesCall.getGames()

        call.enqueue(object : retrofit2.Callback<List<Games>>{

            override fun onFailure(call: Call<List<Games>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Ops! Acho que ocorreu um problema.", Toast.LENGTH_SHORT).show()
                Log.e("Erro_CONEXÃO", t.message.toString())
            }
            override fun onResponse(call: Call<List<Games>>, response: Response<List<Games>>) {
                gameList = response.body()!!

                adapterGames.updateListGames(gameList)
            }
        })
    }
}
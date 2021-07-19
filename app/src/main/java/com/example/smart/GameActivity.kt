package com.example.smart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
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

class GameActivity : AppCompatActivity() {

    lateinit var rvPlatforms: RecyclerView
    lateinit var adapterPlatforms: PlatformsAdapter

    lateinit var gameName: TextView
    lateinit var gameValue: TextView
    lateinit var gameDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)
        supportActionBar?.setTitle("COMPRAR JOGO")

        gameName = findViewById(R.id.)
        gameValue = findViewById(R.id.type_of_product_datasheet)
        gameDescription = findViewById(R.id.type_of_product_datasheet)

        rvPlatforms = findViewById(R.id.recycler_view_games)
        rvPlatforms.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        adapterPlatforms = PlatformsAdapter(this)

        rvPlatforms.adapter = adapterPlatforms

        loadInfo()

        loadPlatformList()
    }

    private fun loadInfo() {
        val retrievedId: Int = intent.getIntExtra("idGame")

        var game: Games
        val retrofit = RetrofitApi.getRetrofit()
        val gamesCall = retrofit.create(GameCalls::class.java)
        val call = gamesCall.getOneGame(retrievedId)

        call.enqueue(object : retrofit2.Callback<Games>{

            override fun onFailure(call: Call<Games>, t: Throwable) {
                Toast.makeText(this@GameActivity, "Ops! Acho que ocorreu um problema.", Toast.LENGTH_SHORT).show()
                Log.e("Erro_CONEXÃO", t.message.toString())
            }
            override fun onResponse(call: Call<Games>, response: Response<Games>) {
                game = response.body()!!


            }
        })
    }

    private fun loadPlatformList() {

        var game: Games
        val retrofit = RetrofitApi.getRetrofit()
        val gamesCall = retrofit.create(GameCalls::class.java)
        val call = gamesCall.getGames()

        call.enqueue(object : retrofit2.Callback<List<Games>>{

            override fun onFailure(call: Call<List<Games>>, t: Throwable) {
                Toast.makeText(this@GameActivity, "Ops! Acho que ocorreu um problema.", Toast.LENGTH_SHORT).show()
                Log.e("Erro_CONEXÃO", t.message.toString())
            }
            override fun onResponse(call: Call<List<Games>>, response: Response<List<Games>>) {
                gameList = response.body()!!

                adapterPlatforms.updateListGames(gameList)
            }
        })
    }
}
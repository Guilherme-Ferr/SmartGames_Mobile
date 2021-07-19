package com.example.smart.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flowtrandingsystem.gui.api.RetrofitApi
import com.example.smart.R
import com.example.smart.adapter.PlatformsAdapter
import com.example.smart.model.Game
import com.example.smartgamesmobile.api.GameCalls
import retrofit2.Call
import retrofit2.Response

class GameActivity : AppCompatActivity() {

    lateinit var rvPlatforms: RecyclerView
    lateinit var adapterPlatforms: PlatformsAdapter

    lateinit var gameName: TextView
    lateinit var gameValue: Button
    lateinit var gameDescription: TextView
    lateinit var gameImage: ImageView
    lateinit var gameImageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("COMPRAR JOGO")

        adapterPlatforms = PlatformsAdapter(this)

        rvPlatforms = findViewById(R.id.recycler_view_platforms)
        rvPlatforms.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvPlatforms.adapter = adapterPlatforms

        gameName = findViewById(R.id.tv_full_game_name)
        gameValue = findViewById(R.id.tv_full_game_value)
        gameDescription = findViewById(R.id.tv_full_game_description)
        gameImage = findViewById(R.id.full_img_card)

        loadInfo()

        aplyDiscount()

        val prefs: SharedPreferences = this@GameActivity.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val retrivedUrl: String = prefs.getString("URL", "Nada foi recebido").toString()

        Glide.with(this).load(retrivedUrl).into(gameImage)
    }

    private fun aplyDiscount() {
        val scannedCode: String = intent.getStringExtra("qrCode").toString()

        Log.e("qrcode", scannedCode)

        if (scannedCode.isNullOrBlank()){

            var game: Game
            val retrofit = RetrofitApi.getRetrofit()
            val gamesCall = retrofit.create(GameCalls::class.java)
            val call = gamesCall.updateGameDiscount(scannedCode.toInt())

            call.enqueue(object : retrofit2.Callback<Game>{

                override fun onFailure(call: Call<Game>, t: Throwable) {
                    Toast.makeText(this@GameActivity, "Ops! Acho que ocorreu um problema.", Toast.LENGTH_SHORT).show()
                    Log.e("Erro_CONEXÃO", t.message.toString())
                }
                override fun onResponse(call: Call<Game>, response: Response<Game>) {
                    game = response.body()!!

                }
            })

        }else{
            Toast.makeText(this, "CODIGO VAZIO", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadInfo() {
        val retrievedId: Int = intent.getIntExtra("idGame", 0)

        var game: Game
        val retrofit = RetrofitApi.getRetrofit()
        val gamesCall = retrofit.create(GameCalls::class.java)
        val call = gamesCall.getOneGame(retrievedId)

        call.enqueue(object : retrofit2.Callback<Game>{

            override fun onFailure(call: Call<Game>, t: Throwable) {
                Toast.makeText(this@GameActivity, "Ops! Acho que ocorreu um problema.", Toast.LENGTH_SHORT).show()
                Log.e("Erro_CONEXÃO", t.message.toString())
            }
            override fun onResponse(call: Call<Game>, response: Response<Game>) {
                game = response.body()!!

                gameName.text = game.name
                gameDescription.text = game.description
                gameImageUrl = game.image
                gameValue.text = "R$ ${String.format("%.2f", game.value)}"
                adapterPlatforms.updateListPlatforms(game.__platforms__)

            }
        })
    }
}
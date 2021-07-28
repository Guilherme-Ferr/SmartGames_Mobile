package com.example.smart.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flowtrandingsystem.gui.api.RetrofitApi
import com.example.smart.R
import com.example.smart.adapter.GamesAdapter
import com.example.smart.model.Game
import com.example.smartgamesmobile.api.GameCalls
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var rvGames: RecyclerView
    lateinit var adapterGames: GamesAdapter
    lateinit var qrCodeImage: ImageView
    lateinit var swipe: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        supportActionBar?.setTitle("SMART GAMES")

        adapterGames = GamesAdapter(this)

        swipe = findViewById(R.id.swipe)

        rvGames = findViewById(R.id.recycler_view_games)
        rvGames.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvGames.adapter = adapterGames

        qrCodeImage = findViewById(R.id.img_camera_code)

        qrCodeImage.setOnClickListener {
            val qrCodeIntent = Intent(this, ScannerActivity::class.java)
            startActivity(qrCodeIntent)
        }

        loadGamesList()

        aplyDiscount()

    }

    private fun aplyDiscount() {
        val scannedCode: String = intent.getStringExtra("qrCode").toString()

        if (scannedCode !== "null"){
            var game: Game
            val retrofit = RetrofitApi.getRetrofit()
            val gamesCall = retrofit.create(GameCalls::class.java)
            val call = gamesCall.updateGameDiscount(scannedCode.toInt())

            call.enqueue(object : retrofit2.Callback<Game> {

                override fun onFailure(call: Call<Game>, t: Throwable) {
                    Toast.makeText(this@MainActivity,"Ops! Acho que ocorreu um problema.",Toast.LENGTH_SHORT).show()
                    Log.e("Erro_CONEXÃO", t.message.toString())
                }

                override fun onResponse(call: Call<Game>, response: Response<Game>) {
                    game = response.body()!!

                    Toast.makeText(this@MainActivity, "20% de Desconto Aplicado em ${game.name}!", Toast.LENGTH_LONG).show()

                }
            })

        }
    }

    private fun loadGamesList() {

        var gameList: List<Game>
        val retrofit = RetrofitApi.getRetrofit()
        val gamesCall = retrofit.create(GameCalls::class.java)
        val call = gamesCall.getGames()

        call.enqueue(object : retrofit2.Callback<List<Game>> {

            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Ops! Acho que ocorreu um problema.", Toast.LENGTH_SHORT).show()
                Log.e("Erro_CONEXÃO", t.message.toString())
            }

            override fun onResponse(call: Call<List<Game>>, response: Response<List<Game>>) {
                gameList = response.body()!!

                adapterGames.updateListGames(gameList)

            }
        })
    }

}
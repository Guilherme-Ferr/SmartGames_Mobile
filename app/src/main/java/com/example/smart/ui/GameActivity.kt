package com.example.smart.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.google.android.gms.maps.GoogleMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flowtrandingsystem.gui.api.RetrofitApi
import com.example.smart.R
import com.example.smart.adapter.PlatformsAdapter
import com.example.smart.adapter.StoresAdapter
import com.example.smart.model.Game
import com.example.smartgamesmobile.api.GameCalls
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.CollapsingToolbarLayout
import retrofit2.Call
import retrofit2.Response
import java.util.*

class GameActivity : AppCompatActivity() {

    lateinit var rvPlatforms: RecyclerView
    lateinit var rvStores: RecyclerView
    lateinit var adapterPlatforms: PlatformsAdapter
    lateinit var adapterStores: StoresAdapter
    lateinit var gameName: CollapsingToolbarLayout
    lateinit var gameValue: Button
    lateinit var gameFinishSale: Button
    lateinit var gameCancelSale: Button
    lateinit var gameStores: CardView
    lateinit var gameDescription: TextView
    lateinit var gameImage: ImageView
    lateinit var gameImageUrl: String
    lateinit var optionMethods: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        adapterPlatforms = PlatformsAdapter(this)
        adapterStores = StoresAdapter(this)

        rvPlatforms = findViewById(R.id.recycler_view_platforms)
        rvPlatforms.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvPlatforms.adapter = adapterPlatforms
        rvStores = findViewById(R.id.recycler_view_stores)
        rvStores.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvStores.adapter = adapterStores

        gameName = findViewById(R.id.tv_full_game_name)
        gameValue = findViewById(R.id.btn_full_game_value)
        gameDescription = findViewById(R.id.tv_full_game_description)
        gameImage = findViewById(R.id.full_img_card)
        gameStores = findViewById(R.id.div_stores_list)

        gameStores.setOnClickListener {
            val storesIntent = Intent(this, MapsActivity::class.java)
            startActivity(storesIntent)
        }

        loadInfo()

        gameValue.setOnClickListener {
            finishSale()
        }

        val prefs: SharedPreferences = this@GameActivity.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val retrivedUrl: String = prefs.getString("URL", "Nada foi recebido").toString()

        Glide.with(this).load(retrivedUrl).into(gameImage)

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

                gameName.title = game.name
                gameDescription.text = game.description
                gameImageUrl = game.image
                gameValue.text = "R$ ${String.format("%.2f", game.value)}"
                adapterPlatforms.updateListPlatforms(game.__platforms__)
                adapterStores.updateListStores(game.__stores__)

            }
        })
    }
    private fun finishSale(){
        val alerDialog = LayoutInflater.from(this).inflate(R.layout.payment_method, null)
        val dialogBuilder = AlertDialog.Builder(this).setView(alerDialog)
        val alertShow = dialogBuilder.show()

        gameFinishSale = alerDialog.findViewById(R.id.button_finish)
        gameCancelSale = alerDialog.findViewById(R.id.button_cancel)

        alertShow.setCanceledOnTouchOutside(false)

        optionMethods = alerDialog.findViewById(R.id.spinner_methods) as Spinner

        val options = arrayOf("Debito", "Credito", "Boleto")

        optionMethods.adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, options)

        optionMethods.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@GameActivity, "Selecione uma opção", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }
        }

        gameFinishSale.setOnClickListener {
            alertShow.dismiss()
            startActivity(getIntent())
            finish()
            Toast.makeText(this, "Compra Efetuada Com Sucesso!", Toast.LENGTH_LONG).show()
        }
        gameCancelSale.setOnClickListener {
            alertShow.dismiss()
        }

    }
}
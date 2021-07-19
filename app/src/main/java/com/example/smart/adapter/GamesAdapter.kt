package com.example.smart.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smart.R
import com.example.smart.model.Game
import com.example.smart.ui.GameActivity
import java.util.*

class GamesAdapter(val context: Context) : RecyclerView.Adapter<GamesAdapter.Holder>(){

    var listGames =  Collections.emptyList<Game>()

    fun updateListGames(lista: List<Game>){
        listGames = lista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.holder_card_games, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return listGames.size
    }
    override fun onBindViewHolder(holder: Holder, index: Int) {
        val recentGames = listGames[index]

        holder.tvName.text = recentGames.name
        holder.tvValue.text = "R$ ${String.format("%.2f", recentGames.value)}"

        Glide.with(holder.itemView.getContext()).load(recentGames.image).into(holder.tvGameImage)

        holder.cardGame.setOnClickListener {
            val intent = Intent(context, GameActivity::class.java)
            intent.putExtra("idGame", recentGames.id_game)
            context.startActivity(intent)

            val prefs: SharedPreferences = context.getSharedPreferences(
                "preferencias",
                Context.MODE_PRIVATE
            )

            prefs.edit().putString("URL", recentGames.image).apply()
        }
    }

    class Holder(view: View): RecyclerView.ViewHolder(view){
        val tvName = view.findViewById<TextView>(R.id.tv_game_name)
        val tvValue = view.findViewById<TextView>(R.id.tv_game_value)
        val tvGameImage = view.findViewById<ImageView>(R.id.img_card)
        val cardGame = view.findViewById<CardView>(R.id.card_button)
    }
}
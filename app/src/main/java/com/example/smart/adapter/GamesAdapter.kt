package com.example.smart.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.smart.GameActivity
import com.example.smart.MainActivity
import com.example.smart.R
import com.example.smart.model.Games
import java.util.*

class GamesAdapter (val context: Context) : RecyclerView.Adapter<GamesAdapter.Holder>(){

    var listGames =  Collections.emptyList<Games>()

    fun updateListGames(lista: List<Games>){
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
        holder.tvValue.text = "R$ ${recentGames.value}"

        holder.tvDetails.setOnClickListener {
            val intent = Intent(context, GameActivity::class.java)
            intent.putExtra("idGame", recentGames.id_game)
            context.startActivity(intent)
        }

    }
    class Holder(view: View): RecyclerView.ViewHolder(view){
        val tvName = view.findViewById<TextView>(R.id.tv_game_name)
        val tvValue = view.findViewById<TextView>(R.id.tv_game_value)
        val tvDetails = view.findViewById<CardView>(R.id.tv_game_details)
    }
}
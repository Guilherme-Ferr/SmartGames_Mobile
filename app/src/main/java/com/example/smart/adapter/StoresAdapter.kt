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
import com.example.smart.model.Store
import com.example.smart.ui.GameActivity
import com.example.smart.ui.MapsActivity
import java.util.*

class StoresAdapter (val context: Context) : RecyclerView.Adapter<StoresAdapter.Holder>() {
    var listStores =  Collections.emptyList<Store>()

    fun updateListStores(lista: List<Store>){
        listStores = lista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.holder_stores, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return listStores.size
    }
    override fun onBindViewHolder(holder: Holder, index: Int) {
        val recentStores = listStores[index]

        holder.tvName.text = recentStores.name

        holder.cardStore.setOnClickListener {
            val intent = Intent(context, MapsActivity::class.java)
            intent.putExtra("latStore", recentStores.latitude)
            intent.putExtra("longStore", recentStores.longitude)
            context.startActivity(intent)

            val prefs: SharedPreferences = context.getSharedPreferences(
                "preferencias",
                Context.MODE_PRIVATE
            )
        }
    }
    class Holder(view: View): RecyclerView.ViewHolder(view){
        val tvName = view.findViewById<TextView>(R.id.store_name)
        val cardStore = view.findViewById<CardView>(R.id.card_stores)
    }

}
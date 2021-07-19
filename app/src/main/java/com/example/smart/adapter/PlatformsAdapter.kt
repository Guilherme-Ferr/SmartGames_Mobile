package com.example.smart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smart.R
import com.example.smart.model.Platform
import java.util.*

class PlatformsAdapter (val context: Context) : RecyclerView.Adapter<PlatformsAdapter.Holder>() {

    var listPlatforms =  Collections.emptyList<Platform>()

    fun updateListPlatforms(lista: List<Platform>){
        listPlatforms = lista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.holder_platforms, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return listPlatforms.size
    }
    override fun onBindViewHolder(holder: Holder, index: Int) {
        val recentPlatforms = listPlatforms[index]

        holder.tvName.text = recentPlatforms.name

    }
    class Holder(view: View): RecyclerView.ViewHolder(view){
        val tvName = view.findViewById<TextView>(R.id.platform_name)
    }
}
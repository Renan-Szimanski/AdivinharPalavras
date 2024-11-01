package com.example.adivinharpalavras

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


data class Perfil(val name: String, val points: String)

class AdapterPerfil(
    private val myList: List<Perfil>
) : RecyclerView.Adapter<AdapterPerfil.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.perfil_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val perfil = myList[position]
        Log.d("AdapterPerfil", "Binding item at position $position: ${perfil.name}, ${perfil.points}")
        holder.textName.text = perfil.name
        holder.textPoints.text = perfil.points
    }

    override fun getItemCount(): Int {
        Log.d("AdapterPerfil", "Item count: ${myList.size}")
        return myList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.user)
        val textPoints: TextView = itemView.findViewById(R.id.pontos)
    }
}


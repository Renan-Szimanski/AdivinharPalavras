package com.example.adivinharpalavras


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


data class Perfil(val name: String, val points: String)

class AdapterPerfil(
    private val myList: MutableList<Perfil>,
    private val onDeleteClick: (Perfil) -> Unit
) : RecyclerView.Adapter<AdapterPerfil.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.perfil_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val perfil = myList[position]
        holder.textName.text = perfil.name
        holder.textPoints.text = perfil.points

        holder.deleteIcon.setOnClickListener {
            onDeleteClick(perfil)
        }
    }

    override fun getItemCount() = myList.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.user)
        val textPoints: TextView = itemView.findViewById(R.id.pontos)
        val deleteIcon: ImageButton = itemView.findViewById(R.id.deleteIcon)
    }

    fun removeItem(perfil: Perfil){
        val position = myList.indexOf(perfil)
        if(position != -1){
            myList.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
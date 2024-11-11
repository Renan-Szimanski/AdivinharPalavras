package com.example.adivinharpalavras


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.compose.ui.graphics.Color
import androidx.recyclerview.widget.RecyclerView


data class Perfil(val name: String, var points: Int)

class AdapterPerfil(
    private val context: Context,
    private val myList: MutableList<Perfil>,
    private val onDeleteClick: (Perfil) -> Unit,
    private val onSelectClick: (Perfil) -> Unit
) : RecyclerView.Adapter<AdapterPerfil.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.perfil_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val perfil = myList[position]
        holder.textName.text = perfil.name
        holder.textPoints.text = perfil.points.toString()

        holder.deleteIcon.setOnClickListener {
            onDeleteClick(perfil)
        }

        holder.itemButton.setOnClickListener {
            onSelectClick(perfil)
            holder.tapIcon.animate().alpha(1f).setDuration(500).withEndAction {
                holder.tapIcon.animate().alpha(.2f).setDuration(500).start()
            }.start()

        }

        holder.tapIcon.animate().alpha(.2f).setDuration(1000).start()
    }

    override fun getItemCount() = myList.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.user)
        val textPoints: TextView = itemView.findViewById(R.id.pontos)
        val deleteIcon: ImageButton = itemView.findViewById(R.id.deleteIcon)
        val tapIcon: ImageView = itemView.findViewById(R.id.selectPerfil)
        val itemButton: CardView = itemView.findViewById(R.id.itemButton)
    }

    fun removeItem(perfil: Perfil){
        val position = myList.indexOf(perfil)
        if(position != -1){
            myList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun addItem(perfil: Perfil) {
        myList.add(perfil)
        notifyItemInserted(myList.size - 1)
    }
}
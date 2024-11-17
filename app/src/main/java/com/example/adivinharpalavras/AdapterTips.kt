package com.example.adivinharpalavras

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


data class Tips(val tips: String)

class AdapterTips(
    context: Context,
    private val myList: MutableList<Tips> = mutableListOf<Tips>()
) : RecyclerView.Adapter<AdapterTips.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tips_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tip = myList[position]
        holder.tipText.text = tip.tips
    }

    override fun getItemCount() = myList.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tipText: TextView = itemView.findViewById(R.id.tipText)
    }

    fun addTips(tips: Tips){
        myList.add(tips)
        notifyItemInserted(myList.size -1)
    }
}
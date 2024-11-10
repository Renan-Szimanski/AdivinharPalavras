package com.example.adivinharpalavras

import android.content.Context

class SelectPerfil(context: Context){

    val db = DBHelper(context)

    companion object {
        var perfilSelecionado: String? = null
    }

    fun selecionado(): Boolean{
        return perfilSelecionado?.isNotEmpty() == true
    }

    fun selecionarPerfil(nome: String){
        perfilSelecionado = nome
    }
    fun pontuacao(addPoints: Int): Int{
        val nome = perfilSelecionado ?: return 0
        val result = db.addPoints(nome, addPoints)
        return result
    }
}


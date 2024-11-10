package com.example.adivinharpalavras

import android.content.Context

class selectPerfil(context: Context){

    val db = DBHelper(context)

    companion object {
        var perfilSelecionado: String? = ""
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


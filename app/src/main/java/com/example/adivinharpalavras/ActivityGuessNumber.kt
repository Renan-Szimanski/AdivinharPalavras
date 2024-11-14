package com.example.adivinharpalavras

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adivinharpalavras.databinding.ActivityGameBinding
import com.example.adivinharpalavras.databinding.ActivityGuessNumberBinding
import kotlin.random.Random

private lateinit var binding: ActivityGuessNumberBinding

class ActivityGuessNumber : AppCompatActivity() {

    private var tip: Int = 0
    private lateinit var titulo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuessNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        val difficulty = intent.getStringExtra("DIFICULDADE")
        setDifficulty(difficulty)

        titulo = binding.textView

    }

    private fun setDifficulty(dif: String?){
        when(dif){
            "EASY" -> easy()
            "MEDIUM" -> medium()
            "HARD" -> hard()
        }
    }

    private fun randomNumber(range: Int){
        val currentNum = Random.nextInt(range)
        Log.d("ActivityGuessNumber", "randomNumber: $currentNum") // ultima parada, vendo os numeros aleatorios

        dicas(currentNum)
    }

    private fun dicas(currentNum: Int){
        if (tips < 1){
            Toast.makeText(this, "Não há mais dicas disponíveis.", Toast.LENGTH_SHORT).show()
        }else{
            tips -= 1
            val aftrNum = Random.nextInt(currentNum, range)
            val bfrNum = Random.nextInt(1, currentNum)

            titulo.text = "Seu número está entre $bfrNum e $aftrNum."
        }
    }

    companion object {
        var dificuldadeEscolhida: Int? = null
        var range: Int = 100 //valores padrões de FACIL
        var tips: Int = 5
    }

    //dificuldades
    private fun easy(){
        randomNumber(range)
        dificuldadeEscolhida = 1
    }
    private fun medium(){
        tips = 3
        val range = 200

        randomNumber(range)
        dificuldadeEscolhida = 2
    }
    private fun hard(){
        tips = 1
        val range = 500

        randomNumber(range)
        dificuldadeEscolhida = 3
    }
}
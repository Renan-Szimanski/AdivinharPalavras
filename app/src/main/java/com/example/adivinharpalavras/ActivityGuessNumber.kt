package com.example.adivinharpalavras

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adivinharpalavras.databinding.ActivityGameBinding
import com.example.adivinharpalavras.databinding.ActivityGuessNumberBinding
import kotlin.random.Random

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityGuessNumberBinding
private lateinit var selectPerfil: SelectPerfil
class ActivityGuessNumber : AppCompatActivity() {

    private lateinit var titulo: TextView
    private lateinit var digNumber: EditText
    private var currentNum: Int = 0
    private var pontos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuessNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        selectPerfil = SelectPerfil(this)
        digNumber = binding.enterNumber
        titulo = binding.textView

        val btnNumber = binding.numberBtn

        val difficulty = intent.getStringExtra("DIFICULDADE")
        setDifficulty(difficulty)

        btnNumber.setOnClickListener {
            verifyAnswer()
        }
    }

    private fun verifyAnswer(){
        val inputText = digNumber.text.toString()
        if (inputText.isNotEmpty()){
            val inputUser = inputText.toIntOrNull()
            if (inputUser != null && inputUser == currentNum){
                pontos = selectPerfil.pontuacao(win)
                Toast.makeText(this, "sim", Toast.LENGTH_SHORT).show()
                digNumber.text.clear()
                randomNumber(range)
            }else{
                pontos = selectPerfil.pontuacao(lose)
                digNumber.text.clear()
                Toast.makeText(this, "nao", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Digite um número", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        var dificuldadeEscolhida: Int? = null
        var range: Int = 0
        var tips: Int = 0
        var win: Int = 0
        var lose: Int = 0
    }

    private fun setDifficulty(dif: String?){
        pontos = selectPerfil.pontuacao(0)
        when(dif){
            "EASY" -> easy()
            "MEDIUM" -> medium()
            "HARD" -> hard()
        }
    }

    private fun randomNumber(range: Int){
        currentNum = Random.nextInt(range)
        Log.d("ActivityGuessNumber", "randomNumber: $currentNum") // ultima parada, vendo os numeros aleatorios
        titulo.text = "Escolha um número de um a ${Companion.range}"
    }

    private fun dicas(currentNum: Int){
        if (pontos > 30){
            if (tips < 1){
                Toast.makeText(this, "Não há mais dicas disponíveis.", Toast.LENGTH_SHORT).show()
            }else{
                tips -= 1
                val aftrNum = Random.nextInt(currentNum, range)
                val bfrNum = Random.nextInt(1, currentNum)

                titulo.text = "Seu número está entre $bfrNum e $aftrNum."
            }    
        }else{
            Toast.makeText(this, "Você não tem pontos suficientes!", Toast.LENGTH_SHORT).show()
        }
    }

    //dificuldades
    private fun easy(){
        range = 100
        tips = 5
        win = 50
        lose = -15
        randomNumber(range)
        dificuldadeEscolhida = 1
    }
    private fun medium(){
        tips = 3
        range = 200
        win = 80
        lose = -35
        randomNumber(range)
        dificuldadeEscolhida = 2
    }
    private fun hard(){
        tips = 1
        range = 500
        win = 100
        lose = -50
        randomNumber(range)
        dificuldadeEscolhida = 3
    }
}
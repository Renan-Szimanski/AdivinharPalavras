package com.example.adivinharpalavras

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.adivinharpalavras.databinding.ActivityGameBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.random.Random

private lateinit var binding: ActivityGameBinding

fun embaralharComFixos(palavra: String): String {
    val letras = palavra.filter { it.isLetter() }.toList().shuffled()
    var indiceLetra = 0
    return palavra.map {
        if (it.isLetter()) {
            letras[indiceLetra++]
        } else {
            it
        }
    }.joinToString("")
}

class ActivityGame : AppCompatActivity() {
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val word = binding.wordTitle //palavra usada
        val nxtButton = binding.nextButtom
        val answer = binding.enterAnswer.text
        val acptButton = binding.acceptBtn
        val pntTxt = binding.pnt
        var correctWord = ""

        var pontos = 130000


        //abre o arquivo de texto e armazena as linhas na variavel
        val inputStream = assets.open("words.txt")
        val linhas = BufferedReader(InputStreamReader(inputStream)).readLines().toMutableList()


        //sistema randomico | pega palavras e tira letras aleatórias para a dificuldade
        fun aleatorio() {
            if(linhas.isNotEmpty()){
                pntTxt.text = "Pontos: $pontos"
                val randomNumber = Random.nextInt(linhas.size) //numero aleatorio do tamanho das linhas
                var palavra = linhas[randomNumber]
                linhas.removeAt(randomNumber)
                correctWord = palavra
                word.text = embaralharComFixos(correctWord.uppercase())
            }
            else{
                Toast.makeText(this,"Não há mais palavras!", Toast.LENGTH_SHORT).show()
            }
        }
        nxtButton.setOnClickListener{
            if (pontos < 20 || linhas.size <= 1){ //caso não tenha 20 pontos, não conseguirá outra palavra
                Toast.makeText(this,"Voce nâo tem pontos suficientes ou não há mais palavras!", Toast.LENGTH_SHORT).show()
            }else{
                //botao de proxima palavra tira 100 pontos ao ser usada
                aleatorio()
                pontos -= 100
                pntTxt.text = "Pontos: $pontos"
                answer.clear()
            }
        }
        acptButton.setOnClickListener{
            //mensagem de campo vazio
            if (answer.isEmpty()){
                Toast.makeText(this,"Coloque uma palavra", Toast.LENGTH_SHORT).show()
            }
            else if (answer.toString().uppercase().filterNot { it.isWhitespace() } == correctWord.uppercase()){
                pontos += 20
                pntTxt.text = "Pontos: $pontos"
                aleatorio()
                answer.clear()
            }
            else{
                Toast.makeText(this, "Resposta Errada! -30pt", Toast.LENGTH_SHORT).show()
                pontos -= 30
                pntTxt.text = "Pontos: $pontos"
                if (pontos < 0){ pontos = 0 }
            }
        }
        aleatorio()
    }
}
package com.example.adivinharpalavras

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.adivinharpalavras.databinding.ActivityGameBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.random.Random

private lateinit var binding: ActivityGameBinding

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

        var pontos = 1300

        //abre o arquivo de texto e armazena as linhas na variavel
        val inputStream = assets.open("words.txt")
        val linhas = BufferedReader(InputStreamReader(inputStream)).readLines().toMutableList()


        //sistema randomico | pega palavras e tira letras aleatórias para a dificuldade
        fun aleatorio(verdadeiro: Boolean){

            pntTxt.text = "Pontos: $pontos"
            //PALAVRAS ALEATÓRIAS

             //todas as linhas do arquivo
            val randomNumber = Random.nextInt(linhas.size) //numero aleatorio do tamanho da palavra
                val palavra = linhas[randomNumber]
                word.text = palavra.uppercase()
            if(verdadeiro == true){
                linhas.removeAt(randomNumber)
            }
        }
        nxtButton.setOnClickListener{
            if (pontos < 20 || linhas.size <= 1){ //caso não tenha 20 pontos, não conseguirá outra palavra
                Toast.makeText(this,"Voce nâo tem pontos suficientes!", Toast.LENGTH_SHORT).show()
            }else{
                //botao de proxima palavra tira -20 pontos ao ser usada
                aleatorio(false)
                pontos -= 20
                pntTxt.text = "Pontos: $pontos"
            }
        }
        acptButton.setOnClickListener{
            //mensagem de campo vazio
            if (answer.isEmpty()){
                Toast.makeText(this,"Coloque uma palavra", Toast.LENGTH_SHORT).show()
            }
            else if (answer.toString().uppercase().filterNot { it.isWhitespace() } == word.text){
                pontos += 20
                pntTxt.text = "Pontos: $pontos"
                aleatorio(true)
                answer.clear()
            }
            else{
                Toast.makeText(this, "Resposta Errada! -30pt", Toast.LENGTH_SHORT).show()
                pontos -= 30
                pntTxt.text = "Pontos: $pontos"

            }
        }
        aleatorio(true)
    }
}
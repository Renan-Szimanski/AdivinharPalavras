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

        val mudartexto = binding.titulo //palavra usada
        val botao = binding.nextButtom
        val resposta = binding.enterAnswer.text
        val aceitar = binding.acceptBtn
        val pntTxt = binding.pnt

        var pontos = 1300

        //abre o arquivo de texto e armazena as linhas na variavel
        val inputStream = assets.open("words.txt")
        val linhas = BufferedReader(InputStreamReader(inputStream)).readLines().toMutableList()


        //sistema randomico | pega palavras e tira letras aleatórias para a dificuldade
        fun aleatorio(verdadeiro: Boolean){
            //PALAVRAS ALEATÓRIAS
             //todas as linhas do arquivo

            val randomNumber = Random.nextInt(linhas.size) //numero aleatorio do tamanho da palavra
                val palavra = linhas[randomNumber].lowercase()
                mudartexto.text = palavra
            if(verdadeiro == true){
                linhas.removeAt(randomNumber)
            }
        }
        botao.setOnClickListener{
            if(botao.hint.toString() == "Começar"){
                aleatorio(false)
                botao.hint = "Próxima"
            }else if (pontos < 20 || linhas.size <= 1){ //caso não tenha 20 pontos, não conseguirá outra palavra
                Toast.makeText(this,"Voce nâo tem pontos suficientes!", Toast.LENGTH_SHORT).show()
                botao.setBackgroundColor(Color.GRAY)
                botao.setHintTextColor(Color.DKGRAY)
            }else{
                //botao de proxima palavra tira -20 pontos ao ser usada
                aleatorio(false)
                pontos -= 20
                pntTxt.text = "Pontos: $pontos"
            }
        }
        aceitar.setOnClickListener{
            //mensagem de campo vazio
            if (resposta.isEmpty()){
                Toast.makeText(this,"Coloque uma palavra", Toast.LENGTH_SHORT).show()
            }
            else if (resposta.toString().lowercase().filterNot { it.isWhitespace() } == mudartexto.text){
                pontos += 20
                pntTxt.text = "Pontos: $pontos"
                aleatorio(true)
                botao.setBackgroundColor(R.color.dkgreen)
                botao.setHintTextColor(Color.WHITE)
                resposta.clear()
            }
            else{
                Toast.makeText(this, "Resposta Errada! -30pt", Toast.LENGTH_SHORT).show()
                pontos -= 30
                pntTxt.text = "Pontos: $pontos"

            }
        }
    }
}
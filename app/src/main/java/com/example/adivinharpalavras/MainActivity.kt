package com.example.adivinharpalavras

import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adivinharpalavras.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mudartexto = binding.titulo
        val botao = binding.nextButtom
        val resposta = binding.enterAnswer.text
        val aceitar = binding.acceptBtn
        val pntTxt = binding.pnt
        var pontos = 50






        //pega palavras aleatorias
        fun aleatorio(){

            val inputStream = assets.open("words.txt")
            val linhas = BufferedReader(InputStreamReader(inputStream)).readLines()

            var randomWords = linhas.shuffled().take(1).toString()
            mudartexto.text = randomWords

        }

        //botao de proxima palavra tira -20 pontos ao ser usada
        botao.setOnClickListener{
            if (pontos < 20){
                Toast.makeText(this,"Voce nâo tem pontos suficiente!", Toast.LENGTH_SHORT).show()
            }else{
                aleatorio()

                pontos -= 20
                pntTxt.text = "Pontos: $pontos"
                botao.hint = "Próxima"

                aceitar.setOnClickListener{
                    //mensagem de campo vazio
                    if (resposta.isEmpty()){
                        Toast.makeText(this,"Coloque uma palavra", Toast.LENGTH_SHORT).show()
                    }else{
                        if (resposta.toString() != mudartexto.text){
                            Toast.makeText(this, "Resposta Errada! -30pt", Toast.LENGTH_SHORT).show()
                            pontos -= 30
                            pntTxt.text = "Pontos: $pontos"
                        }else{
                            pontos += 20
                            aleatorio()
                            pntTxt.text = "Pontos: $pontos"
                            Toast.makeText(this, "Respota Certa! +20pt", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }







        }


        }


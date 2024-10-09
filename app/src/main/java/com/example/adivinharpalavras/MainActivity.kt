package com.example.adivinharpalavras

import android.os.Bundle
import android.view.inputmethod.InputBinding
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
        val resposta = binding.enterAnswer
        val aceitar = binding.acceptBtn
        val pntTxt = binding.pnt
        var pontos = 0



        botao.setOnClickListener{
            val inputStream = assets.open("words.txt")
            val linhas = BufferedReader(InputStreamReader(inputStream)).readLines()

            val randomWords = linhas.shuffled().take(1)
            mudartexto.text = randomWords.toString()




            botao.hint = "Próxima"
        }

        }
    }

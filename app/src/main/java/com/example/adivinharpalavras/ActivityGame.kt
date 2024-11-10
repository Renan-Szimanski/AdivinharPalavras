package com.example.adivinharpalavras

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.adivinharpalavras.databinding.ActivityGameBinding
import kotlin.random.Random

private lateinit var binding: ActivityGameBinding
private lateinit var dbHelper: DBHelper

class ActivityGame : AppCompatActivity() {
    val selPerfil = SelectPerfil(this)
    private lateinit var correctWord: String
    private lateinit var word: TextView
    private lateinit var pntTxt: TextView
    private lateinit var palavra: String
    private var pontos = 0


    private val linhas: MutableList<String> by lazy {
        assets.open("words.txt").bufferedReader().use { it.readLines().toMutableList() }
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        word = binding.wordTitle
        pntTxt = binding.pnt

        val difficulty = intent.getStringExtra("DIFICULDADE")
        setDifficulty(difficulty)

        val nxtButton = binding.nextButtom
        val answer = binding.enterAnswer
        val acptButton = binding.acceptBtn


        nxtButton.setOnClickListener {
            if (pontos < 20 || linhas.size <= 1) {
                Toast.makeText(this, "Você não tem pontos suficientes ou não há mais palavras!", Toast.LENGTH_SHORT).show()
            } else {
                pontos = selPerfil.pontuacao(-100)
                setDifficulty(difficulty)
                answer.text.clear()
            }
        }


        answer.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                verificarResposta(answer.text.toString(), difficulty)
                true
            } else {
                false
            }
        }

        acptButton.setOnClickListener {
            verificarResposta(answer.text.toString(), difficulty)
        }
    }

    private fun verificarResposta(resposta: String, difficulty: String?) {
        if (resposta.isEmpty()) {
            Toast.makeText(this, "Coloque uma palavra", Toast.LENGTH_SHORT).show()
        } else if (resposta.uppercase().filterNot { it.isWhitespace() } == correctWord.uppercase()) {
            pontos = selPerfil.pontuacao(20)
            setDifficulty(difficulty)
            binding.enterAnswer.text.clear()
        } else {
            Toast.makeText(this, "Resposta Errada! -30pt", Toast.LENGTH_SHORT).show()
            pontos = selPerfil.pontuacao(-30)
            if (pontos < 0) pontos = 0
        }
    }

    private fun setDifficulty(difficulty: String?) {
        when (difficulty) {
            "EASY" -> easy()
            "MEDIUM" -> medium()
            "HARD" -> hard()
            else -> Toast.makeText(this, "Dificuldade inválida", Toast.LENGTH_SHORT).show()
        }
    }

    private fun aleatorio() {
        if (linhas.isNotEmpty()) {
            val randomNumber = Random.nextInt(linhas.size) // numero aleatorio do tamanho das linhas
            palavra = linhas[randomNumber]
            linhas.removeAt(randomNumber)
            correctWord = palavra
            pontos = selPerfil.pontuacao(0)
            pntTxt.text = "Pontos: $pontos"
        } else {
            Toast.makeText(this, "Não há mais palavras!", Toast.LENGTH_SHORT).show()
            pntTxt.text = "Pontos: $pontos"
        }
    }

    private fun easy() {
        aleatorio()
        val middle = correctWord.substring(1, correctWord.length - 1)
        word.text = "${correctWord.first()}${embaralharComFixos(middle)}${correctWord.last()}"
    }

    private fun medium() {
        aleatorio()
        word.text = embaralharComFixos(correctWord)
    }

    private fun hard() {
        aleatorio()
        word.text = embaralharComFixos(correctWord.uppercase())
    }

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

}

package com.example.adivinharpalavras

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.adivinharpalavras.databinding.ActivityGameBinding
import com.example.adivinharpalavras.databinding.CongradulationsDialogBinding
import kotlin.random.Random

private lateinit var binding: ActivityGameBinding
private lateinit var dbHelper: DBHelper

class ActivityGame : AppCompatActivity() {
    val selPerfil = SelectPerfil(this)
    private var correctWord: String = ""
    private lateinit var word: TextView
    private lateinit var pntTxt: TextView
    private lateinit var palavra: String
    private var pontos = 0
    private lateinit var dialog: AlertDialog
    private lateinit var mainActivity: MainActivity
    private var difficulty: String? = null

    private val linhas: MutableList<String> by lazy {
        assets.open("words.txt").bufferedReader().use { it.readLines().toMutableList()}
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        word = binding.wordTitle
        pntTxt = binding.pnt

        difficulty = intent.getStringExtra("DIFICULDADE")
        setDifficulty(difficulty)

        val nxtButton = binding.nextButtom
        val answer = binding.enterAnswer
        val acptButton = binding.acceptBtn



        nxtButton.setOnClickListener {
            if (pontos < 100 || linhas.size <= 1) {
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

    fun shwDialog(){
        val builder = AlertDialog.Builder(this, R.style.ThemeCustomDialog)
        val dialogCongBinding: CongradulationsDialogBinding = CongradulationsDialogBinding.inflate(
            LayoutInflater.from(this))
        mainActivity = MainActivity()

        builder.setView(dialogCongBinding.root)
        dialog = builder.create()
        dialog.show()

        dialogCongBinding.returnMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        dialogCongBinding.returnNum.setOnClickListener {
            val intent = Intent(this, ActivityGuessNumber::class.java)
            intent?.let {
                it.putExtra("DIFICULDADE", difficulty)
                startActivity(it)
                dialog.dismiss()
            }

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
            //verificação para não ter pontos negativos
            if (pontos < 30){
                pontos = selPerfil.pontuacao(-pontos)
            }else{
                pontos = selPerfil.pontuacao(-30)
            }

            pntTxt.text = "Pontos: $pontos"
            if (pontos < 0) pontos = 0
        }
    }

    private fun setDifficulty(difficulty: String?) {
        pontos = selPerfil.pontuacao(0)
        pntTxt.text = "Pontos: $pontos"
        when (difficulty) {
            "EASY" -> easy()
            "MEDIUM" -> medium()
            "HARD" -> hard()
            else -> Toast.makeText(this, "Dificuldade inválida", Toast.LENGTH_SHORT).show()
        }
    }

    private fun aleatorio() {
        if (linhas.isNotEmpty()) {
            val randomNumber = Random.nextInt(linhas.size)
            palavra = linhas[randomNumber]
            linhas.removeAt(randomNumber)
            correctWord = palavra
        } else {
            correctWord = ""
            shwDialog()
        }
    }

    private fun easy() {
        aleatorio()

        if (correctWord.length > 2) {
            val middle = correctWord.substring(1, correctWord.length - 1)
            word.text = "${correctWord.first()}${embaralharComFixos(middle)}${correctWord.last()}"
        } else {
            word.text = correctWord
        }
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
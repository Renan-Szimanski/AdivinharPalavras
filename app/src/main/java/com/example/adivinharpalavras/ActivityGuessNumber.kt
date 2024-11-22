package com.example.adivinharpalavras

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adivinharpalavras.databinding.ActivityGuessNumberBinding
import kotlin.random.Random

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityGuessNumberBinding
private lateinit var selectPerfil: SelectPerfil
class ActivityGuessNumber : AppCompatActivity() {

    private lateinit var adapterTips: AdapterTips
    private lateinit var titulo: TextView
    private lateinit var tipButton: Button
    private lateinit var digNumber: EditText
    private lateinit var showPoits: TextView
    private var currentNum: Int = 0
    private var pontos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuessNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()


        showPoits = binding.showPoints
        adapterTips = AdapterTips(this)
        tipButton = binding.tipButton
        selectPerfil = SelectPerfil(this)
        digNumber = binding.enterNumber
        titulo = binding.textView

        val btnNumber = binding.numberBtn

        val difficulty = intent.getStringExtra("DIFICULDADE")
        setDifficulty(difficulty)

        digNumber.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                verifyAnswer()
                true
            } else {
                false
            }
        }

        btnNumber.setOnClickListener {
            verifyAnswer()
        }

        tipButton.setOnClickListener {
            dicas(currentNum)
        }
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        adapterTips.clearTips(emptyList())
    }

    private fun verifyAnswer(){
        val inputText = digNumber.text.toString()
        if (inputText.isNotEmpty()){
            val inputUser = inputText.toIntOrNull()
            if (inputUser != null && inputUser == currentNum){
                pontos = selectPerfil.pontuacao(win)
                Toast.makeText(this, "Acertou, +$win pontos", Toast.LENGTH_SHORT).show()
                digNumber.text.clear()
                randomNumber(range)
            }else{
                pontos = selectPerfil.pontuacao(0)
                if (pontos < lose){
                    pontos = selectPerfil.pontuacao(-pontos)
                }else{
                    pontos = selectPerfil.pontuacao(-lose)
                }
                showPoits.text = pontos.toString()
                digNumber.text.clear()
                if (currentNum < inputUser!!){
                    Toast.makeText(this, "Numero correto é menor, -$lose pontos", Toast.LENGTH_SHORT).show()
                }else if (currentNum > inputUser){
                    Toast.makeText(this, "Numero correto é maior, -$lose pontos", Toast.LENGTH_SHORT).show()
                }
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
        val newTips = mutableListOf<Tips>()
    }

    private fun initRecyclerView(){
        val tipsList: MutableList<Tips> = newTips
        adapterTips = AdapterTips(this, tipsList)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapterTips
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
        pontos = selectPerfil.pontuacao(0)
        showPoits.text = pontos.toString()
        adapterTips.clearTips(emptyList())
    }

    private fun dicas(currentNum: Int){
        if (pontos > 5){
            pontos = selectPerfil.pontuacao(-5)
            showPoits.text = pontos.toString()
            if (tips < 1){
                Toast.makeText(this, "Não há mais dicas disponíveis.", Toast.LENGTH_SHORT).show()
            }else{
                tips -= 1
                val aftrNum = Random.nextInt(currentNum, range)
                val bfrNum = Random.nextInt(1, currentNum)

                adapterTips.addTips(Tips("Seu número está entre $bfrNum e $aftrNum."))
            }    
        }else{
            Toast.makeText(this, "Você não tem pontos suficientes!", Toast.LENGTH_SHORT).show()
        }
    }

    //dificuldades
    private fun easy(){
        range = 100
        tips = 3
        win = 50
        lose = 10
        randomNumber(range)
        dificuldadeEscolhida = 1
    }
    private fun medium(){
        tips = 3
        range = 200
        win = 80
        lose = 35
        randomNumber(range)
        dificuldadeEscolhida = 2
    }
    private fun hard(){
        tips = 1
        range = 500
        win = 100
        lose = 50
        randomNumber(range)
        dificuldadeEscolhida = 3
    }
}
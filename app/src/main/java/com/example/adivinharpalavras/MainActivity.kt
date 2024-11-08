package com.example.adivinharpalavras

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.adivinharpalavras.databinding.ActivityMainBinding
import com.example.adivinharpalavras.databinding.DifficultDialogBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var dialog: AlertDialog
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DBHelper(this)
        rank()

        val start = binding.startWordGame
        val perfilBtn = binding.perfilBtn
        val numGame = binding.startNumberGame

        start.setOnClickListener {
            showDificultyDialog(1)
        }

        numGame.setOnClickListener {
            showDificultyDialog(2)
        }

        perfilBtn.setOnClickListener {
          val intent = Intent(this, ActivityPerfil::class.java)
            startActivity(intent)
        }

    }
    override fun onResume() {
        super.onResume()
        rank()
    }

    private fun showDificultyDialog(games: Int) {
        val builder = AlertDialog.Builder(this, R.style.ThemeCustomDialog)
        val dialogDiffBinding: DifficultDialogBinding = DifficultDialogBinding.inflate(LayoutInflater.from(this))

        builder.setView(dialogDiffBinding.root)
        dialog = builder.create()
        dialog.show()

        dialogDiffBinding.closeDifficulty.setOnClickListener { dialog.dismiss() }

        dialogDiffBinding.doneDifficulty.setOnClickListener {
            val rbId = dialogDiffBinding.radioOptions.checkedRadioButtonId
            if (rbId != -1) {
                val difficulty = when (rbId) {
                    dialogDiffBinding.easy.id -> "EASY"
                    dialogDiffBinding.medium.id -> "MEDIUM"
                    dialogDiffBinding.hard.id -> "HARD"
                    else -> ""
                }

                val intent = when (games) {
                    1 -> Intent(this, ActivityGame::class.java)
                    2 -> Intent(this, ActivityNumGame::class.java)
                    else -> null
                }

                intent?.let {
                    it.putExtra("DIFICULDADE", difficulty)
                    startActivity(it)
                    dialog.dismiss()
                }
            } else {
                Toast.makeText(this, "Escolha uma dificuldade.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun rank(){
        val sec = db.showRank()

        val p1 = binding.player1
        val p2 = binding.player2
        val p3 = binding.player3

        if(sec.isNotEmpty()) {
            p1.text = "${sec.getOrNull(0)?.first ?: "N/A"} \n ${sec.getOrNull(0)?.second ?: 0}"
            p2.text = "${sec.getOrNull(1)?.first ?: "N/A"} \n ${sec.getOrNull(1)?.second ?: 0}"
            p3.text = "${sec.getOrNull(2)?.first ?: "N/A"} \n ${sec.getOrNull(2)?.second ?: 0}"
        }else{
            p1.text = "N/A \n 0"
            p2.text = "N/A \n 0"
            p3.text = "N/A \n 0"
        }
    }
}
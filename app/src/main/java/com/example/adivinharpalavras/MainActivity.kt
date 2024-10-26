package com.example.adivinharpalavras

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val start = binding.startWordGame
        val perfilBtn = binding.perfilBtn

        start.setOnClickListener {
            showDificultyDialog(1)
        }

        perfilBtn.setOnClickListener {
            showDificultyDialog(2)
        }
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
                    2 -> Intent(this, ActivityPerfil::class.java)
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
}

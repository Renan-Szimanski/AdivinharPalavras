package com.example.adivinharpalavras

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.adivinharpalavras.databinding.ActivityMainBinding


private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val start = binding.startWordGame
        val perfilBtn = binding.perfilBtn

        start.setOnClickListener{
            val intent = Intent(this, ActivityGame::class.java)
            startActivity(intent)
        }

        perfilBtn.setOnClickListener {
            val intent = Intent(this, ActivityPerfil::class.java)
            startActivity(intent)
        }
    }
}


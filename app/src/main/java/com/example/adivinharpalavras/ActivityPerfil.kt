package com.example.adivinharpalavras

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Nickname
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adivinharpalavras.databinding.ActivityPerfilBinding
import com.example.adivinharpalavras.databinding.CreatepersonDialogBinding


private lateinit var binding: ActivityPerfilBinding

class ActivityPerfil : AppCompatActivity() {

        private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addPerson = binding.addPersonBtn

        addPerson.setOnClickListener{
            showDialogBinding()
        }
    }
    private fun showDialogBinding(){
        val build = AlertDialog.Builder(this, R.style.ThemeCustomDialog)
        val dialogBinding: CreatepersonDialogBinding = CreatepersonDialogBinding.inflate(
            LayoutInflater.from(this))
        dialogBinding.closeDialog.setOnClickListener { dialog.dismiss() }
        build.setView(dialogBinding.root)

        dialog = build.create()
        dialog.show()
    }





}
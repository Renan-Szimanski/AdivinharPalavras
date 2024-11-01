package com.example.adivinharpalavras

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adivinharpalavras.databinding.ActivityPerfilBinding
import com.example.adivinharpalavras.databinding.CreatepersonDialogBinding


private lateinit var binding: ActivityPerfilBinding


class ActivityPerfil : AppCompatActivity() {

        private lateinit var dialog: AlertDialog
        val dbHelper = DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()


        val addPerson = binding.addPersonBtn

        addPerson.setOnClickListener{
            showDialogBinding()
        }

    }

    private fun initRecyclerView() {
        val listaPerfis = getList()
        Log.d("RecyclerView", "Total profiles in adapter: ${listaPerfis.size}")
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = AdapterPerfil(listaPerfis)
    }

    private fun getList(): List<Perfil>{
        return dbHelper.selectPerfil()
    }

    private fun showDialogBinding(){
        val build = AlertDialog.Builder(this, R.style.ThemeCustomDialog)
        val dialogBinding: CreatepersonDialogBinding = CreatepersonDialogBinding.inflate(
            LayoutInflater.from(this))
        dialogBinding.closeDialog.setOnClickListener { dialog.dismiss() }
        build.setView(dialogBinding.root)

        val crDn = dialogBinding.createDone

        //adicionar ao banco
        crDn.setOnClickListener {
            val nick = dialogBinding.nickPerson.text.toString()
            val isExists = dbHelper.existsPerfil(nick)



            when (isExists){
                true -> Toast.makeText(this, "Nome já usado!", Toast.LENGTH_SHORT).show()
                false -> {
                    val isAdded = dbHelper.addUser(nick)
                    when (isAdded){
                        true -> {   Toast.makeText(this, "Perfil criado com sucesso!", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                            initRecyclerView()
                        }
                        false -> Toast.makeText(this, "Erro ao criar o perfil!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        dialog = build.create()
        dialog.show()
    }





}
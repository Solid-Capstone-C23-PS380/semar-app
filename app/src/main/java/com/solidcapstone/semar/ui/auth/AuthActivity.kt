package com.solidcapstone.semar.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.solidcapstone.semar.MainActivity
import com.solidcapstone.semar.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvToRegister.setOnClickListener {
            clickToRegister()
        }
        binding.btnLogin.setOnClickListener{
            login()
        }
    }

    private fun clickToRegister() {
        RegisterFragment().show(supportFragmentManager, "RegisterFragment")
    }

    private fun login(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
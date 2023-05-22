package com.solidcapstone.semar.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.solidcapstone.semar.R
import com.solidcapstone.semar.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvToRegister.setOnClickListener{
            clickToRegister()
        }
    }

    private fun clickToRegister(){
        RegisterFragment().show(supportFragmentManager, "RegisterFragment")
    }
}
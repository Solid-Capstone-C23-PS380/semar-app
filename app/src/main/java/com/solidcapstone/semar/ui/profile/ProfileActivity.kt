package com.solidcapstone.semar.ui.profile

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.solidcapstone.semar.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.settingDarkMode.setOnClickListener {
            Toast.makeText(this, "Dark Mode", Toast.LENGTH_SHORT).show()
        }
        binding.settingLanguage.setOnClickListener {
            Toast.makeText(this, "Language", Toast.LENGTH_SHORT).show()
        }
        binding.btnLogout.setOnClickListener {
            Toast.makeText(this, "Log Out", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
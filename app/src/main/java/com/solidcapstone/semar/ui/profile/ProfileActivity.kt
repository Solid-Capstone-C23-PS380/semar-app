package com.solidcapstone.semar.ui.profile

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.solidcapstone.semar.R
import com.solidcapstone.semar.databinding.ActivityProfileBinding
import com.solidcapstone.semar.databinding.CustomDialogCardBinding
import com.solidcapstone.semar.ui.profile.edit.EditProfileActivity
import com.solidcapstone.semar.ui.splash.SplashActivity
import com.solidcapstone.semar.utils.SettingsViewModelFactory

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var settingsViewModel: ProfileViewModel
    private lateinit var auth: FirebaseAuth

    private val settingsName = "settings"
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(settingsName)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViewModel()
        observeViewModel()
        refreshUserData()

        binding.settingEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
        binding.settingDarkMode.setOnClickListener {
            val isChecked = binding.switchDarkMode.isChecked
            binding.switchDarkMode.isChecked = !isChecked
            settingsViewModel.saveThemeSetting(!isChecked)
        }
        binding.settingLanguage.setOnClickListener {
            Toast.makeText(
                this,
                getString(R.string.profile_setting_language),
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.btnLogout.setOnClickListener {
            val message: String = resources.getString(R.string.profile_log_out_message)
            showLogOutDialog(message)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        refreshUserData()
    }

    private fun refreshUserData() {
        val currentUser = auth.currentUser
        binding.tvName.text = currentUser?.displayName
        binding.tvEmail.text = currentUser?.email
        Glide.with(this)
            .load(currentUser?.photoUrl)
            .placeholder(R.drawable.ic_person)
            .signature(ObjectKey(System.currentTimeMillis().toString()))
            .into(binding.ivUserImage)
    }

    private fun observeViewModel() {
        settingsViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean? ->
            if (isDarkModeActive == true) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchDarkMode.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchDarkMode.isChecked = false
            }
        }
    }

    private fun initViewModel() {
        val preferences = SettingPreferences.getInstance(this.dataStore)
        settingsViewModel = ViewModelProvider(
            this,
            SettingsViewModelFactory(preferences)
        )[ProfileViewModel::class.java]
    }

    private fun showLogOutDialog(message: String) {
        val dialog = Dialog(this)
        val dialogBinding = CustomDialogCardBinding.inflate(LayoutInflater.from(this))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(dialogBinding.root)

        dialogBinding.tvMessage.text = message

        dialogBinding.btnYes.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        dialogBinding.btnNo.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
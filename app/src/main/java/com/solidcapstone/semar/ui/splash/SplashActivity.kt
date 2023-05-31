package com.solidcapstone.semar.ui.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.solidcapstone.semar.MainActivity
import com.solidcapstone.semar.databinding.ActivitySplashBinding
import com.solidcapstone.semar.ui.auth.AuthActivity
import com.solidcapstone.semar.ui.profile.ProfileViewModel
import com.solidcapstone.semar.ui.profile.SettingPreferences
import com.solidcapstone.semar.utils.SettingsViewModelFactory

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val settingsName = "settings"
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(settingsName)

    private lateinit var binding: ActivitySplashBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Authentication checking
        auth = Firebase.auth
        val firebaseUser = auth.currentUser
        val nextIntent = if (firebaseUser == null) {
            Intent(this, AuthActivity::class.java)
        } else {
            Intent(this, MainActivity::class.java)
        }

        // Dark mode preferences
        val pref = SettingPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(
            this,
            SettingsViewModelFactory(pref)
        )[ProfileViewModel::class.java]
        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean? ->
            when (isDarkModeActive) {
                null -> {
                    // Check if user has OS dark mode activated
                    if (this.resources.configuration.uiMode and
                        Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
                    ) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        viewModel.saveThemeSetting(true)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        viewModel.saveThemeSetting(false)
                    }
                }

                true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Splash screen loading
        Handler(mainLooper).postDelayed({
            startActivity(nextIntent)
            finish()
        }, 2000)
        playAnimation()
    }

    private fun playAnimation() {
        val tagline = ObjectAnimator.ofFloat(binding.tagline, View.ALPHA, 1f).setDuration(500)
        val logo = ObjectAnimator.ofFloat(binding.logo, View.ALPHA, 1f).setDuration(500)
        AnimatorSet().apply {
            playSequentially(logo, tagline)
            start()
        }
    }
}
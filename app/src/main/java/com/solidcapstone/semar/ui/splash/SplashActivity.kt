package com.solidcapstone.semar.ui.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.solidcapstone.semar.R
import com.solidcapstone.semar.databinding.ActivitySplashBinding
import com.solidcapstone.semar.ui.auth.AuthActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }, 2000)
        playAnimation()
    }
    private fun playAnimation() {
        val tagline = ObjectAnimator.ofFloat(binding.tagline, View.ALPHA, 1f).setDuration(500)
        val logo = ObjectAnimator.ofFloat(binding.logo, View.ALPHA, 1f).setDuration(500)
        AnimatorSet().apply {
            playSequentially(logo,tagline)
            start()
        }
    }
}
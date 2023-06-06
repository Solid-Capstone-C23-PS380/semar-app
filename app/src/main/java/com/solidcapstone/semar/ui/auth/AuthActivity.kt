package com.solidcapstone.semar.ui.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.solidcapstone.semar.MainActivity
import com.solidcapstone.semar.R
import com.solidcapstone.semar.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth

        binding.tvToRegister.setOnClickListener { clickToRegister() }
        binding.tvForgetPassword.setOnClickListener {
            ForgetPasswordFragment().show(supportFragmentManager, "ForgetPasswordFragment")
        }
        binding.btnLogin.setOnClickListener { login() }
        binding.btnLoginGoogle.setOnClickListener { googleSignIn() }
    }

    private fun clickToRegister() {
        RegisterFragment().show(supportFragmentManager, "RegisterFragment")
    }

    private fun login() {
        val userEmail = binding.tvEmail.text.toString()
        val userPassword = binding.tvPassword.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            showToast("Format Email tidak valid!")
            return
        } else if (userPassword.length < 8) {
            showToast("Password harus memiliki minimal 8 karakter!")
            return
        }

        setLoadingVisibility(true)

        auth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    goToMainActivity()

                    Log.d(TAG, "signInWithEmail:success")
                } else {
                    showToast("Autentikasi gagal!")
                    setLoadingVisibility(false)

                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                }
            }
    }

    private fun googleSignIn() {
        setLoadingVisibility(true)

        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                setLoadingVisibility(false)
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    goToMainActivity()
                    Log.d(TAG, "signInWithCredential:success")
                } else {
                    setLoadingVisibility(false)
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun setLoadingVisibility(isVisible: Boolean) {
        binding.apply {
            pbAuth.visibility = if (isVisible) View.VISIBLE else View.GONE
            tvEmail.isEnabled = !isVisible
            tvPassword.isEnabled = !isVisible
            tvToRegister.isEnabled = !isVisible
            tvForgetPassword.isEnabled = !isVisible
            btnLogin.isEnabled = !isVisible
            btnLoginGoogle.isEnabled = !isVisible
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val TAG = "AuthActivity"
    }
}
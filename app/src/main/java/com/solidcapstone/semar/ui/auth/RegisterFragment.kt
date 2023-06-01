package com.solidcapstone.semar.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.solidcapstone.semar.MainActivity
import com.solidcapstone.semar.databinding.FragmentRegisterBinding


class RegisterFragment : DialogFragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        binding.btnClose.setOnClickListener { dialog?.dismiss() }
        binding.btnRegister.setOnClickListener { register() }
    }

    override fun onResume() {
        super.onResume()

        val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
    }

    private fun register() {
        val userName = binding.tvName.text.toString()
        val userEmail = binding.tvEmail.text.toString()
        val userPassword = binding.tvPassword.text.toString()
        val userConfirmPassword = binding.tvConfirmPassword.text.toString()

        if (userName.isEmpty()) {
            showToast("Nama harus diisi!")
            return
        } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            showToast("Email format tidak valid!")
            return
        } else if (userPassword.length < 8) {
            showToast("Password harus memiliki minimal 8 karakter!")
            return
        } else if (userPassword != userConfirmPassword) {
            showToast("Konfirmasi password tidak sama!")
            return
        }

        setLoadingVisibility(true)

        auth.createUserWithEmailAndPassword(userEmail, userConfirmPassword)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")

                    val currentUser = auth.currentUser
                    val profileUpdates = userProfileChangeRequest {
                        displayName = userName
                        photoUri = Uri.parse(
                            "https://storage.googleapis.com/wayang-storage/pic/profile/${currentUser?.uid}.jpg"
                        )
                    }
                    currentUser?.updateProfile(profileUpdates)?.addOnSuccessListener {
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)

                    setLoadingVisibility(false)
                    showToast("Autentikasi gagal")
                }
            }
    }

    private fun setLoadingVisibility(isVisible: Boolean) {
        binding.apply {
            pbRegister.visibility = if (isVisible) View.VISIBLE else View.GONE
            tvName.isEnabled = !isVisible
            tvEmail.isEnabled = !isVisible
            tvPassword.isEnabled = !isVisible
            tvConfirmPassword.isEnabled = !isVisible
            btnRegister.isEnabled = !isVisible
            btnClose.isEnabled = !isVisible
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val TAG = "RegisterFragment"
    }
}
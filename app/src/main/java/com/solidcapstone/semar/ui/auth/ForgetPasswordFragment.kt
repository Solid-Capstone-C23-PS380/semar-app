package com.solidcapstone.semar.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.solidcapstone.semar.R
import com.solidcapstone.semar.databinding.FragmentForgetPasswordBinding

class ForgetPasswordFragment : DialogFragment() {
    private lateinit var binding: FragmentForgetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClose.setOnClickListener { dialog?.dismiss() }
        binding.btnReset.setOnClickListener {
            setLoadingVisibility(true)

            val emailAddress = binding.tvEmail.text.toString()
            Firebase.auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToast(getString(R.string.auth_forgot_password_success))
                    dialog?.dismiss()
                } else {
                    showToast(getString(R.string.auth_error_sending_reset_password))
                    setLoadingVisibility(false)
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()

        val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
    }

    private fun setLoadingVisibility(isVisible: Boolean) {
        binding.apply {
            pbForgetPassword.visibility = if (isVisible) View.VISIBLE else View.GONE
            btnReset.isEnabled = !isVisible
            btnClose.isEnabled = !isVisible
            tvEmail.isEnabled = !isVisible
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }
}
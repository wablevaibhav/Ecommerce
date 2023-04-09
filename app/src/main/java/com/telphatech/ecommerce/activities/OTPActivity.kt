package com.telphatech.ecommerce.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.telphatech.ecommerce.MainActivity
import com.telphatech.ecommerce.R
import com.telphatech.ecommerce.databinding.ActivityOtpactivityBinding

class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOTP.setOnClickListener {
            if (binding.EtOTP.text!!.isEmpty()) {
                Toast.makeText(this, "Please provide OTP", Toast.LENGTH_SHORT).show()
            } else {
                verifyUser(binding.EtOTP.text.toString())
            }
        }

    }

    private fun verifyUser(etOTP: String) {

        val credential =
            PhoneAuthProvider.getCredential(intent.getStringExtra("verificationId")!!, etOTP)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val pref = this.getSharedPreferences("user", MODE_PRIVATE)
                    val editor = pref.edit()

                    editor.putString("number",intent.getStringExtra("number")!!)
                    editor.apply()

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
    }


}
package com.telphatech.ecommerce.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.telphatech.ecommerce.R
import com.telphatech.ecommerce.databinding.ActivityLoginBinding
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            if (binding.EtUserNumber.text!!.isEmpty()) {
                Toast.makeText(this, "Please provide number", Toast.LENGTH_SHORT).show()
            } else {
                sendOTP(binding.EtUserNumber.text.toString())
            }
        }

    }

    private lateinit var builder: AlertDialog

    private fun sendOTP(number: String) {

        builder = AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()

        builder.show()

        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber("+91$number")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    private val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            TODO("Not yet implemented")
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            TODO("Not yet implemented")
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(verificationId, token)
            builder.dismiss()
            val intent = Intent(this@LoginActivity, OTPActivity::class.java)
            intent.putExtra("verificationId", verificationId)
            intent.putExtra("number", binding.EtUserNumber.text.toString())
            startActivity(intent)

        }

    }

}
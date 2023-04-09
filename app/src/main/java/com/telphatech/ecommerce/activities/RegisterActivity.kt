package com.telphatech.ecommerce.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.telphatech.ecommerce.databinding.ActivityRegisterBinding
import com.telphatech.ecommerce.models.User

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            openLogin()
        }

        binding.btnSignIn.setOnClickListener {
            validateUser()
        }

    }

    private fun validateUser() {
        if (binding.EtUserName.text!!.isEmpty() || binding.EtUserNumber.text!!.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        } else {
            storeData()
        }
    }

    private fun storeData() {
        val builder = AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()

        builder.show()

        val pref = this.getSharedPreferences("user", MODE_PRIVATE)
        val editor = pref.edit()

        editor.putString("number",binding.EtUserNumber.text.toString())
        editor.putString("name",binding.EtUserName.text.toString())
        editor.apply()


        val data = User(userName = binding.EtUserName.text.toString(), userPhoneNumber = binding.EtUserNumber.text.toString())

        FirebaseFirestore.getInstance().collection("users")
            .document(binding.EtUserNumber.text.toString())
            .set(data).addOnSuccessListener {
                Toast.makeText(this, "User registered.", Toast.LENGTH_SHORT).show()
                builder.dismiss()
                openLogin()
            }
            .addOnFailureListener {
                builder.dismiss()
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }

    private fun openLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
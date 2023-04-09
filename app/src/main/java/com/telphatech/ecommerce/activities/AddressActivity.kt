package com.telphatech.ecommerce.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.telphatech.ecommerce.databinding.ActivityAddressBinding

class AddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddressBinding
    private lateinit var builder: AlertDialog

    private lateinit var pref: SharedPreferences

    private lateinit var totalCost : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = this.getSharedPreferences("user", MODE_PRIVATE)

        totalCost = intent.getStringExtra("totalCost")!!


        loadUserInfo()

        builder = AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()



        binding.btnProceed.setOnClickListener {
            builder.show()
            validateData(
                binding.EtUserNumber.text.toString(),
                binding.EtUserName.text.toString(),
                binding.EtPin.text.toString(),
                binding.EtCity.text.toString(),
                binding.EtState.text.toString(),
                binding.EtVillage.text.toString()
            )
        }

    }

    private fun validateData(
        number: String,
        name: String,
        pin: String,
        city: String,
        state: String,
        village: String
    ) {
        if (number.isEmpty() || state.isEmpty() || name.isEmpty() || pin.isEmpty() || city.isEmpty() || village.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        } else {
            storeData(pin, city, state, village)
        }
    }

    private fun storeData(pin: String, city: String, state: String, village: String) {

        val map = hashMapOf<String, Any>()
        map["village"] = village
        map["state"] = state
        map["city"] = city
        map["pinCode"] = pin

        FirebaseFirestore.getInstance().collection("users")
            .document(pref.getString("number", "")!!)
            .update(map).addOnSuccessListener {
                builder.dismiss()

                val b = Bundle()
                b.putStringArrayList("productIds",intent.getStringArrayListExtra("productIds"))
                b.putString("totalCost",totalCost)
                val intent = Intent(this,CheckoutActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)

            }.addOnFailureListener {
                builder.dismiss()
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }

    private fun loadUserInfo() {


        FirebaseFirestore.getInstance().collection("users")
            .document(pref.getString("number", "")!!)
            .get().addOnSuccessListener {
                builder.dismiss()
                binding.EtUserName.setText(it.getString("userName"))
                binding.EtUserNumber.setText(it.getString("userPhoneNumber"))
                binding.EtVillage.setText(it.getString("village"))
                binding.EtCity.setText(it.getString("city"))
                binding.EtPin.setText(it.getString("pinCode"))
                binding.EtState.setText(it.getString("state"))
            }
    }
}
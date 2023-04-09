package com.telphatech.ecommerce.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.FirebaseFirestore
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.telphatech.ecommerce.MainActivity
import com.telphatech.ecommerce.R
import com.telphatech.ecommerce.databinding.ActivityCheckoutBinding
import com.telphatech.ecommerce.roomDB.AppDatabase
import com.telphatech.ecommerce.roomDB.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class CheckoutActivity : AppCompatActivity(), PaymentResultListener {

    private lateinit var binding: ActivityCheckoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val checkout = Checkout()
        checkout.setKeyID("rzp_test_WG3tHCqixPhVkp")

        val price = intent.getStringExtra("totalCost")

        try {
            val options = JSONObject()
            options.put("name", "Telphatech")
            options.put("description", "Software Company")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#ffbb86fc");
            options.put("currency", "INR");
            options.put("amount", (price!!.toInt()*100))//pass amount in currency subunits

            val retryObj = JSONObject()
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email", "telphatech@telphatech.com")
            prefill.put("contact", "9876543210")
            options.put("prefill", prefill)
            checkout.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }

    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show()

        uploadData()
    }

    private fun uploadData() {
        val id = intent.getStringArrayListExtra("productIds")
        for (currentId in id!!) {
            fetchData(currentId)
        }
    }

    private fun fetchData(currentId: String?) {

        val dao = AppDatabase.getInstance(this).productDao()

        FirebaseFirestore.getInstance().collection("products")
            .document(currentId!!).get().addOnSuccessListener {

                lifecycleScope.launch(Dispatchers.IO) {
                    dao.deleteProduct(ProductModel(currentId))
                }

                saveData(
                    it.getString("productName"),
                    it.getString("productSP"),
                    currentId
                )
            }
    }

    private fun saveData(name: String?, price: String?, id: String?) {
        val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
        val data = hashMapOf<String, Any>()
        data["name"] = name!!
        data["price"] = price!!
        data["productId"] = id!!
        data["status"] = "Ordered"
        data["userId"] = preferences.getString("number", "")!!

        val firestore = FirebaseFirestore.getInstance().collection("allOrders")
        val key = firestore.document().id
        data["orderId"] = key

        firestore.document(key).set(data).addOnSuccessListener {
            Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }.addOnFailureListener {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show()
    }
}
package com.telphatech.ecommerce.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.firestore.FirebaseFirestore
import com.telphatech.ecommerce.MainActivity
import com.telphatech.ecommerce.databinding.ActivityProductBinding
import com.telphatech.ecommerce.roomDB.AppDatabase.Companion.getInstance
import com.telphatech.ecommerce.roomDB.ProductDao
import com.telphatech.ecommerce.roomDB.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)

        getProductDetails(intent.getStringExtra("id").toString())

        setContentView(binding.root)

    }

    private fun getProductDetails(id: String) {
        FirebaseFirestore.getInstance().collection("products")
            .document(id).get().addOnSuccessListener {
                val list = it.get("productImages") as ArrayList<String>
                val name = it.getString("productName")
                val productSP = it.getString("productSP")
                val productDesc = it.getString("productDescription")
                binding.textView7.text = name
                binding.textView8.text = productSP
                binding.textView9.text = productDesc
                val slideList = ArrayList<SlideModel>()
                for (data in list) {
                    slideList.add(
                        SlideModel(data,ScaleTypes.CENTER_CROP)
                    )
                }
                cartAction(id, name, productSP, it.getString("productCoverImg"))
                binding.imageSlider.setImageList(slideList)
            }.addOnFailureListener {
                Toast.makeText(
                    this@ProductActivity,
                    "Something went wrong",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun cartAction(
        id: String,
        name: String?,
        productSP: String?,
        productCoverImg: String?
    ) {
        val productDao = getInstance(this).productDao()
        if (productDao.isExit(id) != null) {
            binding.textView10.text = "Go to Cart"
        } else {
            binding.textView10.text = "Add to Cart"
        }
        binding.textView10.setOnClickListener { view ->
            if (productDao.isExit(id) != null) {
                openCart()
            } else {
                addToCart(productDao, id, name, productSP, productCoverImg)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addToCart(
        productDao: ProductDao,
        id: String,
        name: String?,
        productSP: String?,
        productCoverImg: String?
    ) {
        val data = ProductModel(id, name, productCoverImg, productSP)
        lifecycleScope.launch(Dispatchers.IO) {
            productDao.insertProduct(data)
            binding.textView10.text = "Go To Cart"
        }
    }


    private fun openCart() {
        val preferences = getSharedPreferences("info", MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("isCart", true)
        editor.apply()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
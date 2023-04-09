package com.telphatech.ecommerce.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.telphatech.ecommerce.adapters.ProductAdapter
import com.telphatech.ecommerce.models.AddProduct
import com.telphatech.ecommerce.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
        getProducts(intent.getStringExtra("category").toString())
    }

    private fun getProducts(category: String) {
        val list = ArrayList<AddProduct>()
        FirebaseFirestore.getInstance().collection("products")
            .whereEqualTo("productCategory", category)
            .get()
            .addOnSuccessListener { queryDocumentSnapshots: QuerySnapshot ->
                list.clear()
                for (snapshot in queryDocumentSnapshots) {
                    val data = snapshot.toObject(AddProduct::class.java)
                    list.add(data)
                }
                binding.recyclerView.adapter = ProductAdapter(this, list)
            }
    }
}
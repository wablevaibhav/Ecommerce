package com.telphatech.ecommerce.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.telphatech.ecommerce.R
import com.telphatech.ecommerce.models.AddProduct
import com.telphatech.ecommerce.models.Category
import com.telphatech.ecommerce.adapters.CategoryAdapter
import com.telphatech.ecommerce.adapters.ProductAdapter
import com.telphatech.ecommerce.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val preferences = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        if (preferences.getBoolean("isCart",false)) {
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)
        }


        getSliderImage()
        getCategory()
        getProducts()
        return binding.root
    }

    private fun getSliderImage() {
        FirebaseFirestore.getInstance().collection("slider").document("item")
            .get()
            .addOnSuccessListener {
                Glide.with(requireContext()).load(it.get("img")).into(binding.imgSlider)
            }
    }

    private fun getProducts() {
        val list = ArrayList<AddProduct>()
        FirebaseFirestore.getInstance().collection("products")
            .get()
            .addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(AddProduct::class.java)
                    list.add(data!!)
                }
                binding.RVProducts.adapter = ProductAdapter(requireContext(), list)
            }
    }

    private fun getCategory() {
        val list = ArrayList<Category>()
        FirebaseFirestore.getInstance().collection("categories")
            .get()
            .addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(Category::class.java)
                    list.add(data!!)
                }
                binding.RVCategory.adapter = CategoryAdapter(requireContext(), list)
            }
    }
}
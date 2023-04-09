package com.telphatech.ecommerce.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.telphatech.ecommerce.activities.AddressActivity
import com.telphatech.ecommerce.adapters.CartAdapter
import com.telphatech.ecommerce.databinding.FragmentCartBinding
import com.telphatech.ecommerce.roomDB.AppDatabase
import com.telphatech.ecommerce.roomDB.ProductModel

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private lateinit var list : ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)


        val preferences =
            requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("isCart", false)
        editor.apply()

        val dao = AppDatabase.getInstance(requireContext()).productDao()

        list = ArrayList()

        dao.getAllProducts().observe(requireActivity()) {
            binding.RVCart.adapter = CartAdapter(requireContext(), it)
            list.clear()
            for(data in it) {
                list.add(data.productId)
            }
            totalCost(it)
        }

        return binding.root
    }

    private fun totalCost(data: List<ProductModel>?) {
        var total = 0
        for (item in data!!) {
            total += item.productSP!!.toInt()
        }
        binding.txt2.text = "Total item in cart is ${data.size}"
        binding.txt3.text = "Total Cost : $total"

        binding.btnCheckout.setOnClickListener {
            val intent = Intent(context, AddressActivity::class.java)

            val b = Bundle()
            b.putStringArrayList("productIds",list)
            b.putString("totalCost",total.toString())
            intent.putExtras(b)
            startActivity(intent)
        }

    }
}
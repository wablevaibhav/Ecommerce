package com.telphatech.ecommerce.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.telphatech.ecommerce.R
import com.telphatech.ecommerce.adapters.ProfileAdapter
import com.telphatech.ecommerce.databinding.FragmentProfileBinding
import com.telphatech.ecommerce.models.AllOrder

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var list : ArrayList<AllOrder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)

        list = ArrayList()

        val preferences = requireActivity().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)

        FirebaseFirestore.getInstance().collection("allOrders")
            .whereEqualTo("userId", preferences.getString("number","")!!)
            .get().addOnSuccessListener {
                list.clear()
                for(doc in it) {
                    val data = doc.toObject(AllOrder::class.java)
                    list.add(data)
                }
                binding.recyclerView.adapter = ProfileAdapter(list,requireContext())
            }

        return binding.root
    }
}
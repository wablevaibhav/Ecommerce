package com.telphatech.ecommerce.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.telphatech.ecommerce.databinding.ItemProfileBinding
import com.telphatech.ecommerce.models.AllOrder

class ProfileAdapter(val list : ArrayList<AllOrder>, val context: Context)
    : RecyclerView.Adapter<ProfileAdapter.ProfileAdapterViewHolder>()
{

    inner class ProfileAdapterViewHolder(val binding : ItemProfileBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapterViewHolder {
        return ProfileAdapterViewHolder(ItemProfileBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProfileAdapterViewHolder, position: Int) {
        holder.binding.txtName.text = list[position].name
        holder.binding.txtPrice.text = list[position].price


        when(list[position].status) {
            "Ordered" -> {
                holder.binding.txtStatus.text = "Ordered"
            }
            "Dispatched" -> {
                holder.binding.txtStatus.text = "Dispatched"
            }

            "Delivered" -> {
                holder.binding.txtStatus.text = "Delivered"
            }
            "Cancelled" -> {
                holder.binding.txtStatus.text = "Cancelled"
            }
        }

    }


}
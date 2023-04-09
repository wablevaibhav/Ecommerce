package com.telphatech.ecommerce.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.telphatech.ecommerce.activities.ProductActivity
import com.telphatech.ecommerce.databinding.ItemCartBinding
import com.telphatech.ecommerce.roomDB.AppDatabase
import com.telphatech.ecommerce.roomDB.ProductModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartAdapter(val context: Context, private val list: List<ProductModel>) :
 RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding : ItemCartBinding) :
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(context),parent,false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        Glide.with(context).load(list[position].productImage).into(holder.binding.img1)

        holder.binding.txt1.text = list[position].productName
        holder.binding.txt2.text = list[position].productSP

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }

        val dao = AppDatabase.getInstance(context).productDao()
        holder.binding.img2.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO) {
                dao.deleteProduct(ProductModel(list[position].productId,list[position].productName,list[position].productImage,list[position].productSP))
            }


        }
    }

}
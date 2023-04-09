package com.telphatech.ecommerce.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.telphatech.ecommerce.models.AddProduct
import com.telphatech.ecommerce.activities.ProductActivity
import com.telphatech.ecommerce.adapters.ProductAdapter.ProductAdapterViewHolder
import com.telphatech.ecommerce.databinding.ItemProductsBinding

class ProductAdapter(val context: Context, private val addProducts: ArrayList<AddProduct>) :
    RecyclerView.Adapter<ProductAdapterViewHolder>() {

    inner class ProductAdapterViewHolder(val binding: ItemProductsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapterViewHolder {
        val binding = ItemProductsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProductAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductAdapterViewHolder, position: Int) {
        Glide.with(context)
            .load(addProducts[position].productCoverImg).into(holder.binding.imageView)
        holder.binding.textView.text = addProducts[position].productName
        holder.binding.textView2.text = addProducts[position].productCategory
        holder.binding.textView3.text = addProducts[position].productMRP
        holder.binding.button.text = addProducts[position].productSP
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra("id", addProducts[position].productId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return addProducts.size
    }


}
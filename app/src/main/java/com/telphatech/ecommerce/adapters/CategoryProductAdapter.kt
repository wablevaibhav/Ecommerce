package com.telphatech.ecommerce.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.telphatech.ecommerce.models.AddProduct
import com.telphatech.ecommerce.R
import com.telphatech.ecommerce.activities.ProductActivity
import com.telphatech.ecommerce.adapters.CategoryProductAdapter.CategoryProductAdapterViewHolder
import com.telphatech.ecommerce.databinding.ItemCategoryProductBinding

class CategoryProductAdapter(
    private val context: Context,
    private val category: ArrayList<AddProduct>
) : RecyclerView.Adapter<CategoryProductAdapterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryProductAdapterViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_category_product, parent, false)
        return CategoryProductAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryProductAdapterViewHolder, position: Int) {
        val data = category[position]
        Glide.with(context).load(data.productCoverImg).into(holder.binding.imageView2)
        holder.binding.textView4.text = data.productName
        holder.binding.textView5.text = data.productSP
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra("id", data.productId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return category.size
    }

    class CategoryProductAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemCategoryProductBinding

        init {
            binding = ItemCategoryProductBinding.bind(itemView)
        }
    }
}
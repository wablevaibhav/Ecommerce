package com.telphatech.ecommerce.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.telphatech.ecommerce.adapters.CategoryAdapter.CategoryAdapterViewHolder
import com.telphatech.ecommerce.models.Category
import com.telphatech.ecommerce.R
import com.telphatech.ecommerce.activities.CategoryActivity
import com.telphatech.ecommerce.databinding.ItemCategoryBinding

class CategoryAdapter(private val context: Context, private val category: ArrayList<Category>) :
    RecyclerView.Adapter<CategoryAdapterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
        return CategoryAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryAdapterViewHolder, position: Int) {
        holder.binding.textview2.text = category[position].category
        Glide.with(context).load(category[position].img).into(holder.binding.imagview2)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("category", category[position].category)
            context.startActivities(arrayOf(intent))
        }
    }

    override fun getItemCount(): Int {
        return category.size
    }

    class CategoryAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemCategoryBinding

        init {
            binding = ItemCategoryBinding.bind(itemView)
        }
    }
}
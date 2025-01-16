package com.example.mealz.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.Category
import com.example.mealz.MealsActivity
import com.example.mealz.R
import com.example.mealz.databinding.CatgoryItemBinding

class CategoryAdapter() : ListAdapter<Category, CategoryAdapter.ViewHolder>(
    CategoryDiffCallback()
) {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = CatgoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal=getItem(position)
            holder.bind(getItem(position))

        context= holder.itemView.context
        holder.itemView.findViewById<Button>(R.id.browse_btn).setOnClickListener {
            var mealsActivity = Intent(context, MealsActivity::class.java)
            mealsActivity.putExtra("mealstr",meal.strCategory)
            context.startActivity(mealsActivity)
        }
    }

    class ViewHolder(private val itemBinding: CatgoryItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(category: Category) {
            itemBinding.categoryNameTv.text = category.strCategory
            itemBinding.categoryDesTv.text = category.strCategoryDescription
            Glide.with(itemBinding.root.context).load(category.strCategoryThumb).into(itemBinding.categoryIv)

        }
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean {
            return oldItem == newItem
        }
    }
}
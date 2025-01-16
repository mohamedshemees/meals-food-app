package com.example.mealz.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.Category
import com.example.domain.entity.Meal
import com.example.mealz.MealsActivity
import com.example.mealz.databinding.MealItemBinding

class MealsAdapter() : ListAdapter<Meal, MealsAdapter.ViewHolder>(
    CategoryDiffCallback()
) {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = MealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = getItem(position)
        holder.bind(meal)

    }

    class ViewHolder(private val itemBinding: MealItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(meal: Meal) {
            itemBinding.mealNameTv.text = meal.strMeal
            Glide.with(itemBinding.root.context)
                .load(meal.strMealThumb)
                .into(itemBinding.mealIv)


        }

    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(
            oldItem: Meal,
            newItem: Meal
        ): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(
            oldItem: Meal,
            newItem: Meal
        ): Boolean {
            return oldItem == newItem
        }
    }
}
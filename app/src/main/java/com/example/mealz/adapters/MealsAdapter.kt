package com.example.mealz.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.Meal
import com.example.mealz.IngredientsActivity
import com.example.mealz.R
import com.example.mealz.databinding.MealItemBinding

class MealsAdapter(private val onFavoriteClick: (Meal) -> Unit) :
    ListAdapter<Meal, MealsAdapter.ViewHolder>(
        CategoryDiffCallback()
    ) {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            MealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, onFavoriteClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = getItem(position)
        holder.bind(meal)
        context = holder.itemView.context
        holder.itemBinding.ingredientsBtn.setOnClickListener {
            var ingredientsActivity = Intent(context, IngredientsActivity::class.java)
            ingredientsActivity.putExtra("mealid", meal.idMeal)
            ingredientsActivity.putExtra("mealstr", meal.strMeal)
            ingredientsActivity.putExtra("mealthumb", meal.strMealThumb)
            context.startActivity(ingredientsActivity)
        }
    }

    class ViewHolder(
        val itemBinding: MealItemBinding,
        private val onFavoriteClick: (Meal) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(meal: Meal) {
            itemBinding.mealNameTv.text = meal.strMeal
            Glide.with(itemBinding.root.context)
                .load(meal.strMealThumb)
                .into(itemBinding.mealIv)


            val initialIconRes = if (meal.isFavorite) R.drawable.starfilled else R.drawable.star
            itemBinding.favoriteBtn.setImageResource(initialIconRes)

            itemBinding.favoriteBtn.setOnClickListener {
                meal.isFavorite = !meal.isFavorite

                val iconRes = if (meal.isFavorite) R.drawable.starfilled else R.drawable.star
                itemBinding.favoriteBtn.setImageResource(iconRes)

                onFavoriteClick(meal)
            }
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
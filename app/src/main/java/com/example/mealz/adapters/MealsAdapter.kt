package com.example.mealz.adapters


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.Meal
import com.example.mealz.IngredientsActivity
import com.example.mealz.R
import com.example.mealz.databinding.MealItemBinding
import java.io.File

class MealsAdapter(private val onFavoriteClick: (Meal) -> Unit) :
    RecyclerView.Adapter<MealsAdapter.ViewHolder>() {

    private var meals: List<Meal> = listOf()

    fun setMeals(newMeals: List<Meal>) {
        meals = newMeals
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = MealItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(itemBinding, onFavoriteClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = meals[position]
        holder.bind(meal)

        holder.itemBinding.ingredientsBtn.setOnClickListener {
            val context = holder.itemView.context
            val ingredientsActivity = Intent(context, IngredientsActivity::class.java).apply {
                putExtra("mealid", meal.idMeal)
                putExtra("mealstr", meal.strMeal)
                putExtra("mealthumb", meal.strMealThumb)
            }
            context.startActivity(ingredientsActivity)
        }
    }

    override fun getItemCount(): Int = meals.size

    class ViewHolder(
        val itemBinding: MealItemBinding,
        private val onFavoriteClick: (Meal) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(meal: Meal) {
            itemBinding.mealNameTv.text = meal.strMeal

            Glide.with(itemBinding.root.context)
                .load(File(itemBinding.root.context.filesDir,
                    meal.strMealThumb))
                .into(itemBinding.mealIv)

            Log.d("wow", "Loading image from: ${meal.strMealThumb}")

            updateFavoriteIcon(meal.isFavorite)
            itemBinding.favoriteBtn.setOnClickListener {
                meal.isFavorite = !meal.isFavorite
                updateFavoriteIcon(meal.isFavorite)
                onFavoriteClick(meal)
            }
        }

        private fun updateFavoriteIcon(isFavorite: Boolean) {
            val iconRes = if (isFavorite) R.drawable.starfilled else R.drawable.star
            itemBinding.favoriteBtn.setImageResource(iconRes)
        }
    }
}

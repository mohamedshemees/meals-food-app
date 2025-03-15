package com.example.mealz.adapters

import android.content.Intent
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.Category
import com.example.mealz.MealsActivity
import com.example.mealz.R
import com.example.mealz.databinding.CategoryItemBinding

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CustomViewHolder>() {

    private var categories: List<Category> = listOf()

    fun setCategories(newCategories: List<Category>) {
        categories = newCategories
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemBinding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(itemBinding)
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val meal = categories[position]
        holder.bind(meal)
        holder.itemBinding.BrowseMealsBtn.setOnClickListener {
            val context = holder.itemView.context
            val mealsActivity = Intent(context, MealsActivity::class.java).apply {
                putExtra("category", meal.strCategory)
            }
            context.startActivity(mealsActivity)
        }
    }

    override fun getItemCount(): Int = categories.size


    fun resetAllItems(recyclerView: RecyclerView) {
        for (i in 0 until itemCount) {
            (recyclerView.findViewHolderForAdapterPosition(i) as? CustomViewHolder)?.reset()
        }
    }

    class CustomViewHolder(val itemBinding: CategoryItemBinding) :

        RecyclerView.ViewHolder(itemBinding.root) {

        private var isExpanded = false

        fun bind(category: Category) {
            val context = itemBinding.root.context

            itemBinding.categoryTv.text = category.strCategory
            itemBinding.categoryDesTv.text = category.strCategoryDescription

            Glide.with(context)
                .load(category.strCategoryThumb)
                .into(itemBinding.categoryIv)

            itemBinding.categoryDesTv.maxLines = if (isExpanded) Int.MAX_VALUE else 3

            itemBinding.categoryDesTv.setOnClickListener {
                TransitionManager.beginDelayedTransition(itemBinding.root as ViewGroup)
                isExpanded = !isExpanded
                itemBinding.categoryDesTv.maxLines = if (isExpanded) Int.MAX_VALUE else 3
                itemBinding.categoryDesTv.background =
                    if (isExpanded) null else ContextCompat.getDrawable(
                        context,
                        R.drawable.gradiant_clickabletv
                    )
            }
        }

        fun reset() {
            isExpanded = false
            itemBinding.categoryDesTv.maxLines = 3
            itemBinding.categoryDesTv.background =
                ContextCompat.getDrawable(itemBinding.root.context, R.drawable.gradiant_clickabletv)
        }
    }
}

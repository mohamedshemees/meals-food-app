package com.example.mealz.adapters

import android.content.Context
import android.content.Intent
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.ViewGroup
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
        val itemBinding =
            CatgoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = getItem(position)
        holder.bind(getItem(position), holder.itemView.context)

        context = holder.itemView.context
        holder.itemBinding.BrowseMealsBtn.setOnClickListener {
            var mealsActivity = Intent(context, MealsActivity::class.java)
            mealsActivity.putExtra("category", meal.strCategory)
            context.startActivity(mealsActivity)
        }
    }
    fun resetAllItems(recyclerView: RecyclerView) {
        for (i in 0 until itemCount) {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(i) as? ViewHolder
            viewHolder?.reset()
        }
    }

    class ViewHolder(
        val itemBinding: CatgoryItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        private var isExpanded = false
        fun bind(category: Category, context: Context) {
            itemBinding.categoryNameTv.text = category.strCategory
            itemBinding.categoryDesTv.text = category.strCategoryDescription
            Glide.with(itemBinding.root.context).load(category.strCategoryThumb)
                .into(itemBinding.categoryIv)
            itemBinding.categoryDesTv.maxLines = if (isExpanded) Integer.MAX_VALUE else 3
            itemBinding.categoryDesTv.setOnClickListener {
                TransitionManager.beginDelayedTransition(itemBinding.root as ViewGroup) // Animate transition
                isExpanded = !isExpanded

                itemBinding.categoryDesTv.maxLines = if (isExpanded) Integer.MAX_VALUE else 3
                itemBinding.categoryDesTv.background =
                    if (isExpanded) null else context.resources.getDrawable(R.drawable.gradiant_clickabletv)
            }
        }

        fun reset() {
            isExpanded = false
            itemBinding.categoryDesTv.maxLines = 3
            itemBinding.categoryDesTv.background =
                itemBinding.root.context.resources.getDrawable(R.drawable.gradiant_clickabletv)
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
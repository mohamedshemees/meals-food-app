package com.example.mealz.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealz.R
import com.example.mealz.databinding.IngredientItemBinding

import com.example.mealz.viewmodels.IngredientsViewModel

class IngredientAdapter(
    private val pairs: List<IngredientsViewModel.IngredientDetails>
) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding =
            IngredientItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(
            pairs[position].ingredient,
            pairs[position].measure,
            pairs[position].thumbnail
        )

    }

    override fun getItemCount(): Int = pairs.size

    class IngredientViewHolder(private val binding: IngredientItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: String, measure: String, thumbnail: Bitmap?) {
            binding.ingredientName.text = ingredient
            binding.ingredientMeasure.text = measure

            Glide.with(this.binding.root.context)
                .load(thumbnail)
                .placeholder(R.drawable.unavailable)
                .into(binding.ingredientThubmnail)
        }
    }

}
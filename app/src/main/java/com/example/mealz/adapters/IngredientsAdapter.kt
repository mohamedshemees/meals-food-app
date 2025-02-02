package com.example.mealz.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mealz.databinding.IngredientMeasureBinding
import com.example.mealz.viewmodels.IngredientsViewModel

class IngredientAdapter(
    private val pairs: List<IngredientsViewModel.IngredientMeasurePair>
) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding =
            IngredientMeasureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(pairs[position].ingredient, pairs[position].measure)

    }

    override fun getItemCount(): Int = pairs.size

    class IngredientViewHolder(val binding: IngredientMeasureBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: String, measure: String) {
            binding.ingredientName.text = ingredient
            binding.ingredientMeasure.text = measure
        }
    }

}
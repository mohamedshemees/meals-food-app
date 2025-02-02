package com.example.mealz

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.mealz.adapters.IngredientAdapter
import com.example.mealz.databinding.ActivityDetailsBinding
import com.example.mealz.viewmodels.IngredientsViewModel
import com.example.mealz.viewmodels.MealsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class IngredientsActivity : BaseActivity() {
    private val ingredientsViewModel: IngredientsViewModel by viewModels()
    lateinit var Ingredientsbinding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Ingredientsbinding = ActivityDetailsBinding.inflate(layoutInflater)
        setChildBinding(Ingredientsbinding)
        val meal = intent.getStringExtra("mealstr")
        supportActionBar?.title = meal
        var mealid = ""

        lifecycleScope.launchWhenStarted {
            ingredientsViewModel.uiState.collect { uiState ->
                when (uiState) {
                    is IngredientsViewModel.MealDetailsUiState.Loading -> {
                        Ingredientsbinding.progressBar.visibility = View.VISIBLE
                    }

                    is IngredientsViewModel.MealDetailsUiState.Success -> {
                        Ingredientsbinding.progressBar.visibility = View.GONE
                        Ingredientsbinding.mealNameTv.text = uiState.strmeal
                        Ingredientsbinding.country.text = uiState.country
                        Ingredientsbinding.instructions.text = uiState.instructions
                        Ingredientsbinding.makingvideo.text = uiState.makingLink
                        Ingredientsbinding.sourceLink.text = uiState.source
                        Ingredientsbinding.ingredientMeasureRv.adapter =
                            IngredientAdapter(uiState.ingredientMeasurePairs)
                        Glide.with(this@IngredientsActivity).load(uiState.image)
                            .into(Ingredientsbinding.mealIv)
                        mealid = uiState.mealId

                    }
                }
            }

        }

        Ingredientsbinding.sourceLink.setOnClickListener {
            val sourceurl: String = Ingredientsbinding.sourceLink.text.toString()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(sourceurl))

            val chooser = Intent.createChooser(intent, "Open with")
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(chooser)
            } else {
                Toast.makeText(this, "No app can handle this link", Toast.LENGTH_SHORT).show()
            }
        }
        var isExpanded = false
        Ingredientsbinding.instructions.setOnClickListener {
            TransitionManager.beginDelayedTransition(Ingredientsbinding.root as ViewGroup)
            isExpanded = !isExpanded
            if (isExpanded) {
                Ingredientsbinding.instructions.maxLines = Integer.MAX_VALUE
            } else {
                Ingredientsbinding.instructions.maxLines = 3
                Ingredientsbinding.instructions.post {
                    Ingredientsbinding.instructions.scrollTo(0, 0)
                }
            }
            Ingredientsbinding.instructions.background =
                if (isExpanded) null else baseContext.resources.getDrawable(R.drawable.gradiant_clickabletv)

        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                ingredientsViewModel.favState.collect { isFavorite ->
                    Ingredientsbinding.favoriteBtn.setImageResource(
                        if (isFavorite) R.drawable.starfilled else R.drawable.star
                    )
                }
            }
        }
        Ingredientsbinding.favoriteBtn.setOnClickListener {
            val newFavState = !ingredientsViewModel.favState.value
            Ingredientsbinding.favoriteBtn.setImageResource(
                if (newFavState) R.drawable.starfilled else R.drawable.star
            )
            lifecycleScope.launch {
                ingredientsViewModel.toggleFavoriteStatus(mealid, newFavState)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
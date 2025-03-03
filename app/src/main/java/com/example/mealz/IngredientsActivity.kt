package com.example.mealz

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionManager
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
import com.example.mealz.databinding.ActivityBaseBinding
import com.example.mealz.databinding.ActivityDetailsBinding
import com.example.mealz.viewmodels.IngredientsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class IngredientsActivity : BaseActivity() {
    private val ingredientsViewModel: IngredientsViewModel by viewModels()
    lateinit var ingredientsBinding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ingredientsBinding = ActivityDetailsBinding.inflate(layoutInflater)
        showLoading()
        setChildBinding(ingredientsBinding)
        val meal = intent.getStringExtra("mealstr")
        supportActionBar?.title = meal
        var mealid = ""

        lifecycleScope.launchWhenStarted {
            ingredientsViewModel.uiState.collect { uiState ->
                when (uiState) {
                    is IngredientsViewModel.MealDetailsUiState.Loading -> {
                        showLoading()
                    }
                    is IngredientsViewModel.MealDetailsUiState.Success -> {
                        hideLoading()
                        ingredientsBinding.mealNameTv.text = uiState.tags
                        ingredientsBinding.country.text = uiState.country
                        ingredientsBinding.instructions.text = uiState.instructions
                        ingredientsBinding.makingvideo.text = uiState.makingLink
                        ingredientsBinding.sourceLink.text = uiState.source
                        ingredientsBinding.ingredientMeasureRv.adapter =
                            IngredientAdapter(uiState.ingredientDetailsPairs)
                        Glide.with(this@IngredientsActivity)
                            .load(uiState.image)
                            .into(ingredientsBinding.mealIv)
                        mealid = uiState.mealId

                    }
                }
            }
        }

        ingredientsBinding.sourceLink.setOnClickListener {
            val sourceurl: String = ingredientsBinding.sourceLink.text.toString()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(sourceurl))

            val chooser = Intent.createChooser(intent, "Open with")
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(chooser)
            } else {
                Toast.makeText(this, "No app can handle this link", Toast.LENGTH_SHORT).show()
            }
        }
        var isExpanded = false
        ingredientsBinding.instructions.setOnClickListener {
            TransitionManager.beginDelayedTransition(ingredientsBinding.root as ViewGroup)
            isExpanded = !isExpanded
            if (isExpanded) {
                ingredientsBinding.instructions.maxLines = Integer.MAX_VALUE
            } else {
                ingredientsBinding.instructions.maxLines = 3
                ingredientsBinding.instructions.post {
                    ingredientsBinding.instructions.scrollTo(0, 0)
                }
            }
            ingredientsBinding.instructions.background =
                if (isExpanded) null else baseContext.resources.getDrawable(R.drawable.gradiant_clickabletv)

        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                ingredientsViewModel.favState.collect { isFavorite ->
                    ingredientsBinding.favoriteBtn.setImageResource(
                        if (isFavorite) R.drawable.starfilled else R.drawable.star
                    )
                }
            }
        }
        ingredientsBinding.favoriteBtn.setOnClickListener {
            val newFavState = !ingredientsViewModel.favState.value
            ingredientsBinding.favoriteBtn.setImageResource(
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
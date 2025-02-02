package com.example.mealz

//import com.example.mealz.di.MealsViewModelAssistedFactory
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.Meal
import com.example.mealz.adapters.MealsAdapter
import com.example.mealz.databinding.ActivityMealsBinding
import com.example.mealz.viewmodels.MealsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealsActivity : BaseActivity() {

    private val mealsViewModel: MealsViewModel by viewModels()
    private lateinit var mealsbinding: ActivityMealsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mealsbinding = ActivityMealsBinding.inflate(layoutInflater)
        setChildBinding(mealsbinding)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("category")


        lifecycleScope.launchWhenStarted {
            mealsViewModel.meals.collect { meals ->
                displayMeals(meals)
            }
        }
    }

    private fun displayMeals(meals: List<Meal>) {
        val rv: RecyclerView = findViewById(R.id.meals_rv)
        val mealsAdapter = MealsAdapter { meal ->
            mealsViewModel.toggleFavoriteStatus(meal.idMeal, meal.isFavorite)
        }
        mealsAdapter.submitList(meals)

        rv.setLayoutManager(LinearLayoutManager(this))
        rv.adapter = mealsAdapter
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true

        }
        return super.onOptionsItemSelected(item)
    }
}



package com.example.mealz

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.Meal
import com.example.mealz.adapters.MealsAdapter
import com.example.mealz.databinding.ActivityFavoriteBinding
import com.example.mealz.viewmodels.MealsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteActivity : BaseActivity() {
    private lateinit var favoritebinding: ActivityFavoriteBinding
    private val mealsViewModel: MealsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favoritebinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setChildBinding(favoritebinding)
        val mealsToolbar: Toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(mealsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Favorites"

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.selectedItemId = R.id.Favorites


        lifecycleScope.launchWhenStarted {
            mealsViewModel.favoriteMeals.collect { meals ->
                displayMeals(meals)

            }
        }

    }

    private fun displayMeals(meals: List<Meal>) {
        val rv: RecyclerView = findViewById(R.id.favorite_rv)
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

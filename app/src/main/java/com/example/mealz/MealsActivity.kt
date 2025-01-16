package com.example.mealz

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.Category
import com.example.domain.entity.Meal
import com.example.domain.entity.Meals
import com.example.mealz.adapters.CategoryAdapter
import com.example.mealz.adapters.MealsAdapter
//import com.example.mealz.di.MealsViewModelAssistedFactory
import com.example.mealz.viewmodels.MealsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class MealsActivity : AppCompatActivity() {

    private  val mealsViewModel: MealsViewModel by viewModels()

    private val mealsAdapter = MealsAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meals)
        val mealsToolbar: Toolbar = findViewById(R.id.mealstoolbar)


        setSupportActionBar(mealsToolbar)

        lifecycleScope.launchWhenStarted {
            mealsViewModel.meals.collect { meals ->
                displayMeals(meals)
            }
        }

    }
    private fun displayMeals(meals: List<Meal>) {
        val rv: RecyclerView = findViewById(R.id.meals_rv)
        mealsAdapter.submitList(meals)
        rv.adapter=mealsAdapter
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true

        }
        return super.onOptionsItemSelected(item)
    }
}



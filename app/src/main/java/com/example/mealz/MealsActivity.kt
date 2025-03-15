package com.example.mealz

//import com.example.mealz.di.MealsViewModelAssistedFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealz.adapters.MealsAdapter
import com.example.mealz.databinding.ActivityMealsBinding
import com.example.mealz.viewmodels.MealsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealsActivity : BaseActivity() {

    private val mealsViewModel: MealsViewModel by viewModels()
    private lateinit var mealsbinding: ActivityMealsBinding
    lateinit var mealsAdapter: MealsAdapter

    private val searchHandler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mealsbinding = ActivityMealsBinding.inflate(layoutInflater)
        setChildBinding(mealsbinding)
        findViewById<BottomNavigationView>(R.id.bottom_nav).menu.setGroupCheckable(0, true, false)
        for (i in 0 until findViewById<BottomNavigationView>(R.id.bottom_nav).menu.size()) {
            findViewById<BottomNavigationView>(R.id.bottom_nav).menu.getItem(i).isChecked =
                false
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("category")


        val rv: RecyclerView = mealsbinding.mealsRv
         mealsAdapter = MealsAdapter { meal ->
            mealsViewModel.toggleFavoriteStatus(meal.idMeal, meal.isFavorite)
        }
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter=mealsAdapter

            lifecycleScope.launchWhenStarted {
                mealsViewModel.meals.collect { meals ->
                    mealsAdapter.setMeals(meals)
                }
            }
                mealsbinding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean = false

                    override fun onQueryTextChange(newText: String?): Boolean {
                        searchRunnable?.let { searchHandler.removeCallbacks(it) }
                        searchRunnable= Runnable {
                                filterList(newText?:"")
                        }
                        searchHandler.postDelayed(searchRunnable!!, 300)
                        return true
                        }
            })

    }
    private fun filterList(query: String) {
        val fliterdlist=mealsViewModel.meals.value.filter {
            it.strMeal.contains(query, ignoreCase = true)
        }
        mealsAdapter.setMeals(fliterdlist)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true

        }
        return super.onOptionsItemSelected(item)
    }
}



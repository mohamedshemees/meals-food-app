package com.example.mealz

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.mealz.adapters.CategoryAdapter
import com.example.mealz.databinding.ActivityMainBinding
import com.example.mealz.viewmodels.CategoriesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val categoriesViewModel: CategoriesViewModel by viewModels()
    lateinit var mainbinding: ActivityMainBinding
    lateinit var searchview: SearchView
    lateinit var categoryAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainbinding = ActivityMainBinding.inflate(layoutInflater)
        setChildBinding(mainbinding)

        supportActionBar?.title = "Categories"
        supportActionBar?.setDisplayHomeAsUpEnabled(false);

        val rv: RecyclerView = mainbinding.categoryRv
        categoryAdapter = CategoryAdapter()
        rv.adapter = categoryAdapter


        lifecycleScope.launchWhenStarted {
            categoriesViewModel.categories.collect { categories ->
                categoryAdapter.setCategories(categories)
            }
        }

        mainbinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText ?: "")
                return true
            }
        })
    }

    private fun filterList(query: String) {
        val filteredList = categoriesViewModel.categories.value
            .filter { it.strCategory.startsWith(query, ignoreCase = true) }
        categoryAdapter.setCategories(filteredList)
    }

    override fun onStop() {
        super.onStop()
        val recyclerView: RecyclerView = findViewById(R.id.category_rv)
        val adapter = recyclerView.adapter as? CategoryAdapter
        adapter?.resetAllItems(recyclerView)
    }
}




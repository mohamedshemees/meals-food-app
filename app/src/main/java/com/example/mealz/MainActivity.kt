package com.example.mealz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.Category
import com.example.mealz.adapters.CategoryAdapter
import com.example.mealz.viewmodels.CategoriesViewModel
import com.example.mealz.viewmodels.MealsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val categoriesViewModel: CategoriesViewModel by viewModels()

    private val categoryAdapter = CategoryAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        lifecycleScope.launchWhenStarted {
            categoriesViewModel.categories.collect{categories->
                displayCategories(categories)
            }
        }

    }
    private fun displayCategories(categories: List<Category>) {
        val rv: RecyclerView = findViewById(R.id.category_rv)

        categoryAdapter.submitList(categories)
        rv.adapter=categoryAdapter
    }

        }



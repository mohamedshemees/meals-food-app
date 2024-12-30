package com.example.mealz

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.Category
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val categoriesViewModel: CategoriesViewModel by viewModels()

    private val mealsCategoryAdapter = MealsCategoryAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        lifecycleScope.launchWhenStarted {
            categoriesViewModel.categories.collect{categories->
                displayCategories(categories)

            }
        }

    }
    private fun displayCategories(categories: List<Category>) {
        val rv: RecyclerView = findViewById(R.id.category_rv)
        // updating a RecyclerView
        mealsCategoryAdapter.submitList(categories)
        rv.adapter=mealsCategoryAdapter
    }
        }



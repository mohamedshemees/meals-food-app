package com.example.mealz

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.Category
import com.example.mealz.adapters.CategoryAdapter
import com.example.mealz.viewmodels.CategoriesViewModel
import com.google.android.material.divider.MaterialDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val categoriesViewModel: CategoriesViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_main, findViewById(R.id.activity_content))
        supportActionBar?.title = "Categories"
        supportActionBar?.setDisplayHomeAsUpEnabled(false);

        lifecycleScope.launchWhenStarted {
            categoriesViewModel.categories.collect { categories ->
                displayCategories(categories)
            }
        }

    }

    private fun displayCategories(categories: List<Category>) {

        val rv: RecyclerView = findViewById(R.id.category_rv)
        val categoryAdapter = CategoryAdapter()
        rv.adapter = categoryAdapter
//        var divider = MaterialDividerItemDecoration(rv.context, RecyclerView.VERTICAL)
//        divider.setDividerColor(
//            ContextCompat.getColor(
//                this,
//                R.color.base_color
//            )
//        )

        categoryAdapter.submitList(categories)

    }

    override fun onStop() {
        super.onStop()
        val recyclerView: RecyclerView = findViewById(R.id.category_rv)
        val adapter = recyclerView.adapter as? CategoryAdapter
        adapter?.resetAllItems(recyclerView)
    }
}




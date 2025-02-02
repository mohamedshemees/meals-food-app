package com.example.domain.usecase

import android.content.Context
import com.example.domain.entity.Category
import com.example.domain.repo.MealsRepo
import kotlinx.coroutines.flow.Flow

class GetCategories(
    private val categoriesRepo: MealsRepo
) {
    fun getCategories(): Flow<List<Category>> {
        return categoriesRepo.getCategories()
    }

    suspend fun refreshData(context: Context) {
        categoriesRepo.refreshData(context)
    }

}

package com.example.domain.usecase

import com.example.domain.entity.Category
import com.example.domain.repo.CategoriesRepo
import kotlinx.coroutines.flow.Flow

class GetCategories(
    private val categoriesRepo: CategoriesRepo
) {
    fun getCategories(): Flow<List<Category>> {
        return categoriesRepo.getCategories()
    }

    // Function to refresh the categories (write operation)
    suspend fun refreshCategories() {
        categoriesRepo.refreshCategories()
    }
}

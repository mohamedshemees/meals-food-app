package com.example.domain.repo

import com.example.domain.entity.Category
import kotlinx.coroutines.flow.Flow

interface CategoriesRepo {

    fun getCategories(): Flow<List<Category>>


    suspend fun refreshCategories()
}
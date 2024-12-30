package com.example.data.repo

import com.example.data.local.CategoriesDao
import com.example.data.remote.ApiService
import com.example.domain.entity.Category
import com.example.domain.repo.CategoriesRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoriesRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val categoriesDao: CategoriesDao
                    ) :CategoriesRepo {

    /**
     refreshes the categories from the API and update the local database.
     Ensures this is called only once during app startup.
     */

    private var hasRefreshed = false

    override fun getCategories(): Flow<List<Category>> {
        // Retrieve categories as a Flow from Room
        return categoriesDao.getCategoriesFromLocal()
    }

    override suspend fun refreshCategories() {
        // Check if data needs to be refreshed (only refresh once)
        if (!hasRefreshed) {
            try {
                val apiCategories = apiService.getCategories() // Fetch from API
                categoriesDao.InsertCategories(apiCategories.categories) // Insert into Room
                hasRefreshed = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}


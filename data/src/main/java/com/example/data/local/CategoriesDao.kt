package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.entity.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {

    @Query("SELECT * FROM Categories")
     fun getCategoriesFromLocal(): Flow<List<Category>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertCategories(category: List<Category>)

}
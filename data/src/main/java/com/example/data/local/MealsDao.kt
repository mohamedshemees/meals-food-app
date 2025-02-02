package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.entity.Meal
import kotlinx.coroutines.flow.Flow

@Dao
interface MealsDao {


    @Query("SELECT * FROM Meals where categoryId=:category")
    fun getMealsFromLocal(category: String): Flow<List<Meal>>

    @Query("SELECT COUNT(*) FROM Meals")
    suspend fun getMealsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeals(meals: List<Meal>)

    @Query("SELECT * FROM Meals where isFavorite=1")
    fun getFavoriteMeals(): Flow<List<Meal>>

    @Query("SELECT isFavorite FROM Meals where idMeal=:mealId")
    suspend fun getFavState(mealId: String): Boolean

    @Query("UPDATE meals SET isFavorite = :isFavorite WHERE idMeal = :mealId")
    suspend fun updateFavoriteStatus(mealId: String, isFavorite: Boolean)

}
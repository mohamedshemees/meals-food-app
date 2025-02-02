package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.entity.MealDetails

@Dao
interface MealDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealDetails(mealDetails: List<MealDetails>)

    @Query("SELECT * FROM MealDetails WHERE idMeal = :mealId")
    suspend fun getMealDetailsById(mealId: String): MealDetails

    @Query("SELECT COUNT(*) FROM meals")
    suspend fun getMealsDetailsCount(): Int


}
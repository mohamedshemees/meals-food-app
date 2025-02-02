package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.domain.entity.Category
import com.example.domain.entity.Meal
import com.example.domain.entity.MealDetails

@Database(
    entities = [Category::class, Meal::class, MealDetails::class],
    version = 1,
    exportSchema = false
)
abstract class MealsDatabase : RoomDatabase() {
    abstract fun mealsDao(): MealsDao
    abstract fun categoriesDao(): CategoriesDao
    abstract fun mealdetailsDao(): MealDetailsDao


}
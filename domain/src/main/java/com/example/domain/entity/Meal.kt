package com.example.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Meals")
data class Meal(
    @PrimaryKey
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val categoryId: String,
    var isFavorite: Boolean = false
)
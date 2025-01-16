package com.example.domain.usecase

import com.example.domain.entity.Meal

import com.example.domain.repo.MealsRepo
import kotlinx.coroutines.flow.Flow

class GetMeals(
    private val mealsrepo: MealsRepo

) {
        fun getMeals(category: String): Flow<List<Meal>>  {
        return mealsrepo.getMeals(category)
    }

}

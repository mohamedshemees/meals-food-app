package com.example.mealz.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.MealDetails
import com.example.domain.usecase.GetMeals
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class IngredientsViewModel @Inject constructor(
    private var getmealsUseCase: GetMeals,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val meal: String = savedStateHandle["mealid"] ?: "Defaultmeal"

    private val _uiState = MutableStateFlow<MealDetailsUiState>(MealDetailsUiState.Loading)
    val uiState: StateFlow<MealDetailsUiState> = _uiState

    private val _favState = MutableStateFlow(false)
    val favState: StateFlow<Boolean> = _favState


    init {
        viewModelScope.launch {
            fetchMealDetails()
            updateFavState(meal)
        }
    }

    private suspend fun fetchMealDetails() {
        val mealDetails = getmealsUseCase.getingredients(meal)
        updateMealDetails(mealDetails)
    }

    private fun extractIngredientMeasurePairs(mealDetails: MealDetails): List<IngredientMeasurePair> {
        val pairs = mutableListOf<IngredientMeasurePair>()
        for (i in 1..20) {
            val ingredient = mealDetails::class.members
                .find { it.name == "strIngredient$i" }
                ?.call(mealDetails) as? String

            val measure = mealDetails::class.members
                .find { it.name == "strMeasure$i" }
                ?.call(mealDetails) as? String

            if (!ingredient.isNullOrEmpty() && !measure.isNullOrEmpty()) {
                pairs.add(IngredientMeasurePair(ingredient, measure))
            }
        }
        return pairs
    }

    private fun updateMealDetails(mealDetails: MealDetails) {
        _uiState.value = MealDetailsUiState.Success(
            strmeal = mealDetails.strMeal ?: "",
            country = mealDetails.strArea ?: "",
            source = mealDetails.strSource ?: "Source unavailable",
            makingLink = mealDetails.strYoutube ?: "Video Link is unavailable",
            instructions = mealDetails.strInstructions ?: "",
            image = mealDetails.strMealThumb ?: "",
            tags = mealDetails.strTags ?: "",
            ingredientMeasurePairs = extractIngredientMeasurePairs(mealDetails),
            mealId=mealDetails.idMeal

        )
    }

    suspend fun toggleFavoriteStatus(mealId: String,isFavorite: Boolean) {
        getmealsUseCase.toggleFavoriteStatus(mealId, isFavorite)
        updateFavState(mealId)
    }

    private suspend fun updateFavState(mealId: String) {
        _favState.value = getmealsUseCase.getFavState(mealId)
    }

    data class IngredientMeasurePair(
        val ingredient: String,
        val measure: String
    )

    sealed class MealDetailsUiState {
        object Loading : MealDetailsUiState()
        data class Success(
            val strmeal: String,
            val country: String,
            val source: String,
            val makingLink: String,
            val instructions: String,
            val image: String,
            val tags: String,
            val ingredientMeasurePairs: List<IngredientMeasurePair>,
            val mealId: String
        ) : MealDetailsUiState()
    }

}
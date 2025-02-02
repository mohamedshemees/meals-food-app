package com.example.mealz.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Meal
import com.example.domain.usecase.GetMeals
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MealsViewModel @Inject constructor(
    private val getmealsUseCase: GetMeals,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val category: String = savedStateHandle["category"] ?: "DefaultCategory"

    val meals: StateFlow<List<Meal>> = getmealsUseCase.getMeals(category)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(2000), emptyList())


    val favoriteMeals: Flow<List<Meal>> = getmealsUseCase.getFavoriteMeals()


    fun toggleFavoriteStatus(mealId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            getmealsUseCase.toggleFavoriteStatus(mealId, isFavorite)
        }
    }



}




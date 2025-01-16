package com.example.mealz.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Category
import com.example.domain.entity.Meal
import com.example.domain.entity.Meals
import com.example.domain.usecase.GetCategories
import com.example.domain.usecase.GetMeals
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MealsViewModel @Inject constructor(
     getmealsUseCase: GetMeals,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val category: String = savedStateHandle["mealstr"] ?: "DefaultCategory"

    val meals: StateFlow<List<Meal>> = getmealsUseCase.getMeals(category)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(2000), emptyList())

    }




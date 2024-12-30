package com.example.mealz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Category
import com.example.domain.usecase.GetCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getmealsUseCase: GetCategories,

    ) : ViewModel() {

    val categories: StateFlow<List<Category>> = getmealsUseCase.getCategories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(2000), emptyList())
    init {
        refreshCategoriesOnStart()
    }
    private fun refreshCategoriesOnStart() {
        viewModelScope.launch {
            getmealsUseCase.refreshCategories() // Suspend function called here
        }
    }
}


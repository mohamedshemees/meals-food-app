package com.example.mealz.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Category
import com.example.domain.usecase.GetCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategories: GetCategories,
    @ApplicationContext private val context: Context

) : ViewModel() {

    val categories: StateFlow<List<Category>> = getCategories.getCategories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(2000), emptyList())

    init {
        refreshCategoriesOnStart(context)
    }

    private fun refreshCategoriesOnStart(context: Context) {
        viewModelScope.launch {
            getCategories.refreshData(context)
        }
    }
}


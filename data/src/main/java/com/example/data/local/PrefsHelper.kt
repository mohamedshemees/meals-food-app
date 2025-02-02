package com.example.data.local

import android.content.SharedPreferences

class PrefsHelper(private val sharedPrefs: SharedPreferences) {
    var isInitCategorysComplete: Boolean
        get() = sharedPrefs.getBoolean("is_initial_categories_fetch_complete", false)
        set(value) = sharedPrefs.edit().putBoolean("is_initial_categories_fetch_complete", value)
            .apply()

    var isInitMealsComplete: Boolean
        get() = sharedPrefs.getBoolean("is_initial_meal_fetch_complete", false)
        set(value) = sharedPrefs.edit().putBoolean("is_initial_meal_fetch_complete", value).apply()

    var isInitMealsDetailsComplete: Boolean
        get() = sharedPrefs.getBoolean("is_initial_meal_details_fetch_complete", false)
        set(value) = sharedPrefs.edit().putBoolean("is_initial_meal_details_fetch_complete", value)
            .apply()

    var DataComplete: Boolean
        get() = sharedPrefs.getBoolean("is_initial_data_complete", false)
        set(value) = sharedPrefs.edit().putBoolean("is_initial_data_complete", value).apply()

}
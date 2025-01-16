package com.example.mealz.di

import android.content.Context
import android.content.SharedPreferences
import com.example.data.local.PrefsHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefsModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePrefsHelper(sharedPreferences: SharedPreferences): PrefsHelper {
        return PrefsHelper(sharedPreferences)
    }
}
package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.domain.entity.Category

@Database(entities = [Category::class], version = 1, exportSchema = false)
abstract class CategoriesDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao

}
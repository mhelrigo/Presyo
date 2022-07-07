package com.mhelrigo.data.product.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mhelrigo.data.product.datasource.local.model.ProductCategoriesModel
import com.mhelrigo.data.product.datasource.local.model.ProductCategoryModel
import com.mhelrigo.data.product.datasource.local.model.ProductModel

@Database(
    entities = [ProductModel::class, ProductCategoryModel::class, ProductCategoriesModel::class],
    version = 1,
    exportSchema = false
)
abstract class PresyoDatabase : RoomDatabase() {
    companion object {
        const val NAME = "PresyoDatabase"
    }

    abstract fun productDao(): ProductDao
}
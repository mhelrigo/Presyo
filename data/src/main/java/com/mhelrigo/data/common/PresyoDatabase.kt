package com.mhelrigo.data.common

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mhelrigo.data.product.datasource.local.model.ProductCategoriesModel
import com.mhelrigo.data.product.datasource.local.model.ProductCategoryModel

@Database(entities = [ProductCategoryModel::class, ProductCategoriesModel::class], version = 1, exportSchema = false)
abstract class PresyoDatabase : RoomDatabase(){
}
package com.mhelrigo.data.product.datasource.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.mhelrigo.data.product.datasource.local.model.ProductCategoriesModel
import com.mhelrigo.data.product.datasource.local.model.ProductCategoryModel
import com.mhelrigo.data.product.datasource.local.model.ProductModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertProduct(productModel: ProductModel): Long

    @Insert(onConflict = REPLACE)
    suspend fun insertProductCategory(productCategoryModel: ProductCategoryModel): Long

    @Insert(onConflict = REPLACE)
    suspend fun insertProductCategories(productCategoriesModel: ProductCategoriesModel): Long

    @Query("SELECT * FROM ProductModel WHERE productCategoryId = :productCategoryId")
    suspend fun getProducts(productCategoryId: Int): List<ProductModel>

    @Query("SELECT * FROM ProductCategoryModel WHERE productCategoriesId = :productCategoriesId")
    suspend fun getProductCategories(productCategoriesId: Int): List<ProductCategoryModel>

    @Query("SELECT * FROM ProductCategoriesModel")
    suspend fun getProductCategories(): ProductCategoriesModel
}
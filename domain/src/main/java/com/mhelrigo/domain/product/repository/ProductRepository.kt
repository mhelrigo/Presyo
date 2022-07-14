package com.mhelrigo.domain.product.repository

import com.mhelrigo.domain.product.entity.ProductCategories
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(dataType: DataType): Flow<ProductCategories>

    interface RemoteDataSource {
        suspend fun getProducts(): Flow<ProductCategories>
    }

    interface LocalDataSource {
        suspend fun cacheProducts(productCategories: ProductCategories)
        suspend fun getProductCategories(): ProductCategories
    }
}
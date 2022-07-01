package com.mhelrigo.domain.product.interactor

import com.mhelrigo.domain.product.entity.Product
import com.mhelrigo.domain.product.entity.ProductCategories
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Flow<ProductCategories>

    interface RemoteDataSource {
        suspend fun getProducts(): Flow<ProductCategories>
    }

    interface LocalDataSource {
        suspend fun cacheProducts(productCategories: ProductCategories)
        suspend fun getProducts(): Flow<List<Product>>
        suspend fun getProductCategories(): Flow<ProductCategories>
    }
}
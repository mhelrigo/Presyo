package com.mhelrigo.domain.product.interactor

import com.mhelrigo.domain.product.entity.Product
import com.mhelrigo.domain.product.entity.ProductCategories

interface ProductRepository {
    suspend fun getProducts(): ProductCategories

    interface RemoteDataSource {
        suspend fun getProducts(): ProductCategories
    }

    interface LocalDataSource {
        suspend fun cacheProducts(productCategories: ProductCategories)
        suspend fun getProducts(): List<Product>
        suspend fun getProductCategories(): ProductCategories
    }
}
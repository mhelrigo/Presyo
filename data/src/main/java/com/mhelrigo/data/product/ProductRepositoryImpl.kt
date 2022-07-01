package com.mhelrigo.data.product

import com.mhelrigo.domain.product.entity.ProductCategories
import com.mhelrigo.domain.product.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    val remoteDataSource: ProductRepository.RemoteDataSource,
) : ProductRepository {
    override suspend fun getProducts(): Flow<ProductCategories> = remoteDataSource.getProducts()
}
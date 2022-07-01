package com.mhelrigo.data.product

import com.mhelrigo.domain.product.entity.ProductCategories
import com.mhelrigo.domain.product.interactor.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    val remoteDataSource: ProductRepository.RemoteDataSource,
) : ProductRepository {
    override suspend fun getProducts(): Flow<ProductCategories> = remoteDataSource.getProducts()
}
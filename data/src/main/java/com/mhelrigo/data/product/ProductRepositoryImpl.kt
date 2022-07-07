package com.mhelrigo.data.product

import com.mhelrigo.domain.product.entity.ProductCategories
import com.mhelrigo.domain.product.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    val remoteDataSource: ProductRepository.RemoteDataSource,
    val localDataSource: ProductRepository.LocalDataSource
) : ProductRepository {
    override suspend fun getProducts(): Flow<ProductCategories> =
        remoteDataSource.getProducts().map {
            localDataSource.cacheProducts(it)
            it
        }.catch {
            emit(localDataSource.getProductCategories())
        }
}
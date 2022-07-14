package com.mhelrigo.data.product

import android.util.LruCache
import com.mhelrigo.domain.product.repository.DataType
import com.mhelrigo.domain.product.entity.ProductCategories
import com.mhelrigo.domain.product.repository.ProductRepository
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    val remoteDataSource: ProductRepository.RemoteDataSource,
    val localDataSource: ProductRepository.LocalDataSource
) : ProductRepository {
    companion object {
        private const val LRU_CACHE_SIZE = 1024
    }

    private var runtimeProductCache = LruCache<Int, ProductCategories>(LRU_CACHE_SIZE)
    private fun cacheProductInRuntime(productCategories: ProductCategories) {
        runtimeProductCache.put(1, productCategories)
    }

    /**
     * Get data from remote source and cache it in a local sources(runtime and persistent).
     *
     * Runtime Cache
     * 1. If there is a value inside a runtime cache, then it will be returned. Another network call/database call
     * for getting data during this Apps lifecycle is not necessary since data changes once a day at most.
     *
     * DataType = LATEST
     * 1. Get data from remote source.
     * 2. If there is data then cache it.
     * 3. else catch and throw exception to be handled by the presentation layer.
     *
     * DataType = CACHED
     * 1. Get data from local source, then catch it on runtime cache.
     * 2. If encountered, catch and throw exception to be handled by the presentation layer
     * */
    override suspend fun getProducts(dataType: DataType): Flow<ProductCategories> {
        return if (runtimeProductCache.get(1) != null) {
            println("Emitting runtime-cache data!")
            flow { emit(runtimeProductCache.get(1)) }
        } else {
            when (dataType) {
                DataType.LATEST -> {
                    getLatestProduct()
                }
                DataType.CACHED -> {
                    getCachedProduct()
                }
            }
        }
    }

    private suspend fun getLatestProduct() = remoteDataSource.getProducts().map {
        localDataSource.cacheProducts(it)
        cacheProductInRuntime(it)
        it
    }.catch {
        it.printStackTrace()
        runCatching {
            emit(localDataSource.getProductCategories())
        }.onFailure {
            throw it
        }
    }

    private suspend fun getCachedProduct() = flow {
        runCatching {
            val productCategories = localDataSource.getProductCategories()
            emit(productCategories)
            cacheProductInRuntime(productCategories)
        }.onFailure {
            throw it
        }
    }
}
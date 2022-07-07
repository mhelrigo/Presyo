package com.mhelrigo.data

import com.mhelrigo.domain.product.entity.ProductCategories
import com.mhelrigo.domain.product.repository.ProductRepository
import com.mhelrigo.domain.product.utils.test.CoroutineTestRule
import junit.framework.TestCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule

internal class ProductRepositoryTest : TestCase() {
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private lateinit var remoteDataSourceImpl: RemoteDataSourceImpl
    private lateinit var localDataSourceImpl: LocalDataSourceImpl

    override fun setUp() {
        remoteDataSourceImpl = RemoteDataSourceImpl()
        localDataSourceImpl = LocalDataSourceImpl()
    }

    fun `test getting data from remote source successfully`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            remoteDataSourceImpl.getProducts().collect {
                assertTrue(remoteDataSourceImpl.isDataEmitted)
                assertEquals(
                    ProductMapperTest.FakeData.PRODUCT_CATEGORIES_FIREBASE_MDOEL.data?.size,
                    it.productCategories.size
                )

                val cachedData = async { localDataSourceImpl.cacheProducts(it) }
                cachedData.await()

                assertTrue(localDataSourceImpl.isDataCached)
            }
        }
    }

    fun `test getting data from local source successfully`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val productCategories = localDataSourceImpl.getProductCategories()
            assertTrue(localDataSourceImpl.isDataEmitted)
            assertEquals(
                ProductMapperTest.FakeData.PRODUCT_CATEGORIES_FIREBASE_MDOEL.data?.size,
                productCategories.productCategories.size
            )
        }
    }

    internal class RemoteDataSourceImpl : ProductRepository.RemoteDataSource {
        var isDataEmitted = false
        override suspend fun getProducts(): Flow<ProductCategories> {
            isDataEmitted = true
            return flow<ProductCategories> {
                emit(ProductMapperTest.FakeData.PRODUCT_CATEGORIES)
            }
        }
    }

    internal class LocalDataSourceImpl : ProductRepository.LocalDataSource {

        var isDataEmitted = false
        var isDataCached = false

        override suspend fun cacheProducts(productCategories: ProductCategories) {
            isDataCached = true
        }

        override suspend fun getProductCategories(): ProductCategories {
            isDataEmitted = true

            return ProductMapperTest.FakeData.PRODUCT_CATEGORIES
        }
    }
}
package com.mhelrigo.data

import com.mhelrigo.data.product.datasource.remote.model.ProductCategoriesFirebaseModel
import com.mhelrigo.domain.product.entity.ProductCategories
import com.mhelrigo.domain.product.repository.ProductRepository
import com.mhelrigo.domain.product.utils.test.CoroutineTestRule
import junit.framework.TestCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule

internal class ProductRepositoryTest : TestCase() {
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    internal lateinit var remoteDataSourceImpl: RemoteDataSourceImpl

    override fun setUp() {
        remoteDataSourceImpl = RemoteDataSourceImpl()
    }

    fun `test getting data from remote source successfully`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            remoteDataSourceImpl.getProducts().collect {
                assertTrue(remoteDataSourceImpl.isDataEmitted)
                assertEquals(
                    ProductMapperTest.FakeData.productCategoriesFirebaseModel.data?.size,
                    it.productCategories.size
                )
            }
        }
    }

    internal class RemoteDataSourceImpl : ProductRepository.RemoteDataSource {
        var isDataEmitted = false
        override suspend fun getProducts(): Flow<ProductCategories> {
            isDataEmitted = true
            return flow<ProductCategories> {
                emit(ProductCategoriesFirebaseModel.transform(ProductMapperTest.FakeData.productCategoriesFirebaseModel))
            }
        }
    }
}
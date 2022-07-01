package com.mhelrigo.domain.product.usecase

import com.mhelrigo.domain.product.entity.ProductCategories
import com.mhelrigo.domain.product.interactor.ProductRepository
import com.mhelrigo.domain.product.utils.test.CoroutineTestRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule

@ExperimentalCoroutinesApi
internal class GetProductsUseCaseTest : TestCase() {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    lateinit var productRepositoryImpl: ProductRepositoryImpl

    private var isLoadingCalled = false
    private var isWithResult = false

    override fun setUp() {
        productRepositoryImpl = ProductRepositoryImpl()
    }

    override fun tearDown() {
    }

    fun testInvoke() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            productRepositoryImpl.getProducts()
                .onStart {
                    isLoadingCalled = true
                }.collect {
                    isWithResult = true
                    assertNotNull(it)
                }
        }

        assertTrue(productRepositoryImpl.isNotError)
        assertTrue(isLoadingCalled)
        assertTrue(isWithResult)
    }

    internal class ProductRepositoryImpl : ProductRepository {
        var isNotError = false;
        override suspend fun getProducts(): Flow<ProductCategories> {
            return flow {
                isNotError = true
                emit(ProductCategories(emptyList()))
            }
        }
    }
}
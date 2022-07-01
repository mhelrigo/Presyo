package com.mhelrigo.presyo.main

import com.mhelrigo.data.product.ProductRepositoryImpl
import com.mhelrigo.domain.product.entity.ProductCategories
import com.mhelrigo.domain.product.repository.ProductRepository
import junit.framework.TestCase
import kotlinx.coroutines.flow.*

internal class MainViewModelTest : TestCase() {

    private lateinit var fakeGetUseCase: FakeGetUseCase

    private lateinit var loadingProduct: () -> Unit
    private lateinit var productReceived: (ProductCategories) -> Unit
    private lateinit var errorEncountered: () -> Unit

    private var isLoadingCalled = false
    private var isProductReceived = false
    private var isErrorNotEncountered = true

    override fun setUp() {
        fakeGetUseCase = FakeGetUseCase(ProductRepositoryImpl(FakeRemoteDataSourceImpl()))

        loadingProduct = {
            isLoadingCalled = true
        }

        productReceived = {
            isProductReceived = true
        }

        errorEncountered = {
            isErrorNotEncountered = false
        }
    }

    fun `test get product successfully`() {
        kotlinx.coroutines.test.runTest {
            fakeGetUseCase().onStart {
                loadingProduct()
                assertTrue(isLoadingCalled)
            }.catch {
                errorEncountered()
            }.collectLatest {
                productReceived(it)
                assertNotNull(it)
                assertTrue(isProductReceived)
                assertTrue(isErrorNotEncountered)
            }
        }
    }

    internal class FakeGetUseCase(var productRepositoryImpl: ProductRepositoryImpl) {
        suspend operator fun invoke() = productRepositoryImpl.getProducts()
    }

    internal class FakeRemoteDataSourceImpl: ProductRepository.RemoteDataSource {
        override suspend fun getProducts(): Flow<ProductCategories> {
            return flow {
                emit(ProductCategories(emptyList()))
            }
        }
    }
}
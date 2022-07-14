package com.mhelrigo.presyo.product

import com.mhelrigo.data.product.ProductRepositoryImpl
import com.mhelrigo.domain.product.entity.ProductCategories
import com.mhelrigo.domain.product.repository.ProductRepository
import junit.framework.TestCase
import kotlinx.coroutines.flow.*

internal class ProductViewModelTest : TestCase() {

    private lateinit var fakeGetUseCase: FakeGetUseCase

    private lateinit var loadingProduct: () -> Unit
    private lateinit var productReceived: (ProductCategories) -> Unit
    private lateinit var errorEncountered: () -> Unit

    private var isLoadingCalled = false
    private var isProductReceived = false
    private var isErrorNotEncountered = true

    private var fakeLocalDataSourceImpl = FakeLocalDataSourceImpl()

    override fun setUp() {
        fakeGetUseCase = FakeGetUseCase(
            ProductRepositoryImpl(
                FakeRemoteDataSourceImpl(),
                fakeLocalDataSourceImpl
            )
        )

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
                assertTrue(fakeLocalDataSourceImpl.isDataCached)
            }
        }
    }

    fun `test get product but failed`() {
        fakeGetUseCase = FakeGetUseCase(
            ProductRepositoryImpl(
                FakeRemoteDataSourceFailedImpl(),
                fakeLocalDataSourceImpl
            )
        )

        kotlinx.coroutines.test.runTest {
            fakeGetUseCase().onStart {
                loadingProduct()
                assertTrue(isLoadingCalled)
            }.catch {
                errorEncountered()
            }.collectLatest {
                productReceived(it)
                assertEquals("Cached Fake Date", it.date)
                assertTrue(isProductReceived)
                assertTrue(isErrorNotEncountered)
                assertTrue(fakeLocalDataSourceImpl.isCachedDataEmitted)
            }
        }
    }

    internal class FakeGetUseCase(var productRepositoryImpl: ProductRepositoryImpl) {
        suspend operator fun invoke() = productRepositoryImpl.getProducts()
    }

    internal class FakeRemoteDataSourceImpl : ProductRepository.RemoteDataSource {
        override suspend fun getProducts(): Flow<ProductCategories> {
            return flow {
                emit(ProductCategories(emptyList(), "Fake Date"))
            }
        }
    }

    internal class FakeRemoteDataSourceFailedImpl : ProductRepository.RemoteDataSource {
        override suspend fun getProducts(): Flow<ProductCategories> {
            return flow {
                throw NullPointerException()
            }
        }
    }

    internal class FakeLocalDataSourceImpl : ProductRepository.LocalDataSource {
        var isDataCached = false
        var isCachedDataEmitted = false;

        override suspend fun cacheProducts(productCategories: ProductCategories) {
            isDataCached = true
        }

        override suspend fun getProductCategories(): ProductCategories {
            isCachedDataEmitted = true
            return ProductCategories(emptyList(), "Cached Fake Date")
        }
    }
}
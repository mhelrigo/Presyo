package com.mhelrigo.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mhelrigo.data.product.datasource.local.database.PresyoDatabase
import com.mhelrigo.data.product.datasource.local.database.ProductDao
import com.mhelrigo.data.product.datasource.local.model.ProductCategoriesModel
import com.mhelrigo.data.product.datasource.local.model.ProductCategoryModel
import com.mhelrigo.data.product.datasource.local.model.ProductModel
import com.mhelrigo.domain.product.utils.test.CoroutineTestRule
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.nio.file.Files.size

@RunWith(AndroidJUnit4::class)
class ProductDaoTest {

    private lateinit var presyoDatabase: PresyoDatabase
    private lateinit var productDao: ProductDao

    @Before
    fun setUp() {
        mockRoom()
    }

    @Test
    fun test_successful_insert_and_retrieve() {
        runBlocking {
            val insertProduct = flow {
                emit(productDao.insertProduct(FakeData.PRODUCT_MODEL))
            }

            val insertProductCategory = flow {
                emit(productDao.insertProductCategory(FakeData.PRODUCT_CATEGORY_MODEL))
            }

            val insertProductCategories = flow {
                emit(productDao.insertProductCategories(FakeData.PRODUCT_CATEGORIES_MODEL))
            }

            val combineInserts = async {
                combine(insertProduct, insertProductCategory, insertProductCategories) { data ->
                    data
                }
            }

            combineInserts.await()
                .collect {
                    assertEquals(1, it[0])
                    assertEquals(1, it[1])
                    assertEquals(1, it[2])
                }


            val getProducts = async { flow { emit(productDao.getProducts(1)) } }
            getProducts.await().collect {
                assertEquals(1, it.size)
            }

            val getProductCategories = async { flow { emit(productDao.getProductCategories(1)) } }
            getProductCategories.await().collect {
                assertEquals(1, it.size)
            }

            val getProductCategoriesParent =
                async { flow { emit(productDao.getProductCategories()) } }
            getProductCategoriesParent.await().collect {
                assertEquals(FakeData.PRODUCT_CATEGORIES_MODEL.date, it.date)
            }
        }
    }

    @After
    fun clear() {
        presyoDatabase.close()
    }

    private fun mockRoom() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        presyoDatabase = Room.inMemoryDatabaseBuilder(context, PresyoDatabase::class.java).build()
        productDao = presyoDatabase.productDao()
    }

    internal class FakeData {
        companion object {
            val PRODUCT_MODEL = ProductModel(1, "Fake Name", 1, 50, 50, "Fake Origin")
            val PRODUCT_CATEGORY_MODEL = ProductCategoryModel(1, "Fake Name", 1, "Fake Unit")
            val PRODUCT_CATEGORIES_MODEL = ProductCategoriesModel("Fake Date")
        }
    }
}
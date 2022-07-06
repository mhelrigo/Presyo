package com.mhelrigo.data

import com.mhelrigo.data.product.datasource.remote.model.ProductCategoriesFirebaseModel
import com.mhelrigo.data.product.datasource.remote.model.ProductCategoryFirebaseModel
import com.mhelrigo.data.product.datasource.remote.model.ProductFirebaseModel
import junit.framework.TestCase

internal class ProductMapperTest : TestCase() {

    fun `test mapper when data received from remote source`() {
        val productCategories =
            ProductCategoriesFirebaseModel.transform(FakeData.productCategoriesFirebaseModel)
        assertNotNull(productCategories)
        assertEquals(1, productCategories.productCategories.size)

        val productCategory = productCategories.productCategories[0]
        assertEquals(FakeData.productCategoryFirebaseModel.name, productCategory.name)
        assertNotNull(productCategory.products)
        assertEquals(1, productCategory.products.size)
        assertEquals(FakeData.productCategoryFirebaseModel.unit, productCategory.unitType.name)

        val product = productCategory.products[0]
        assertEquals(FakeData.productFirebaseModel.name, product.name)
        assertEquals(FakeData.productFirebaseModel.origin, product.productOrigin.name)
    }

    internal class FakeData {
        companion object {
            internal val productFirebaseModel = ProductFirebaseModel(1, "Product 1", 1, 2, "LOCAL")
            internal val productCategoryFirebaseModel = ProductCategoryFirebaseModel(
                "Category 1",
                "NOT_AVAILABLE",
                listOf(productFirebaseModel)
            )
            internal val productCategoriesFirebaseModel =
                ProductCategoriesFirebaseModel(listOf(productCategoryFirebaseModel))
        }
    }
}
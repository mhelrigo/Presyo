package com.mhelrigo.data

import com.mhelrigo.data.product.datasource.local.model.ProductCategoriesModel
import com.mhelrigo.data.product.datasource.local.model.ProductCategoryModel
import com.mhelrigo.data.product.datasource.local.model.ProductModel
import com.mhelrigo.data.product.datasource.remote.model.ProductCategoriesFirebaseModel
import com.mhelrigo.data.product.datasource.remote.model.ProductCategoryFirebaseModel
import com.mhelrigo.data.product.datasource.remote.model.ProductFirebaseModel
import com.mhelrigo.domain.product.entity.Product
import com.mhelrigo.domain.product.entity.ProductCategories
import com.mhelrigo.domain.product.entity.ProductCategory
import junit.framework.TestCase

internal class ProductMapperTest : TestCase() {

    fun `test mapper when data received from remote source`() {
        val products = listOf(FakeData.PRODUCT_FIREBASE_MODEL.transform())
        val productCategoryList =
            listOf(FakeData.PRODUCT_CATEGORY_FIREBASE_MODEL.transform(products))
        val productCategories =
            FakeData.PRODUCT_CATEGORIES_FIREBASE_MDOEL.transform(productCategoryList)
        assertNotNull(productCategories)
        assertEquals(1, productCategories.productCategories.size)

        val productCategory = productCategories.productCategories[0]
        assertEquals(FakeData.PRODUCT_CATEGORY_FIREBASE_MODEL.name, productCategory.name)
        assertNotNull(productCategory.products)
        assertEquals(1, productCategory.products.size)
        assertEquals(FakeData.PRODUCT_CATEGORY_FIREBASE_MODEL.unit, productCategory.unitType.name)

        val product = productCategory.products[0]
        assertEquals(FakeData.PRODUCT_FIREBASE_MODEL.name, product.name)
        assertEquals(FakeData.PRODUCT_FIREBASE_MODEL.origin, product.productOrigin.name)
    }

    fun `test mapper when data received from local source`() {
        val products = listOf<Product>(FakeData.PRODUCT_MODEL.transform())
        val productCategoryList =
            listOf<ProductCategory>(FakeData.PRODUCT_CATEGORY_MODEL.transform(products))
        val productCategories = FakeData.PRODUCT_CATEGORIES_MODEL.transform(productCategoryList)

        // ProductCategories
        assertEquals(ProductCategories::class.java, productCategories.javaClass)
        assertEquals(1, productCategories.productCategories.size)
        assertEquals(FakeData.PRODUCT_CATEGORIES_MODEL.date, productCategories.date)

        // ProductCategory
        assertEquals(ProductCategory::class.java, productCategories.productCategories[0].javaClass)
        assertEquals(1, productCategories.productCategories[0].products.size)
        assertEquals(
            FakeData.PRODUCT_CATEGORY_MODEL.name,
            productCategories.productCategories[0].name
        )

        // Product
        assertEquals(
            Product::class.java,
            productCategories.productCategories[0].products[0].javaClass
        )
        assertEquals(
            FakeData.PRODUCT_MODEL.name,
            productCategories.productCategories[0].products[0].name
        )
    }

    fun `test mapper when saving data into local source`() {
        val productCategoriesModel = ProductCategoriesModel.transform(FakeData.PRODUCT_CATEGORIES)
        val productCategoryList =
            listOf(ProductCategoryModel.transform(FakeData.PRODUCT_CATEGORIES.productCategories[0]))
        val products =
            listOf(
                ProductModel.transform(
                    FakeData.PRODUCT_CATEGORIES.productCategories[0].products[0],
                    1
                )
            )

        // ProductCategoriesModel
        assertEquals(ProductCategoriesModel::class.java, productCategoriesModel.javaClass)
        assertEquals(FakeData.PRODUCT_CATEGORIES.date, productCategoriesModel.date)

        // Product Category
        assertEquals(ProductCategoryModel::class.java, productCategoryList[0].javaClass)
        assertEquals(
            FakeData.PRODUCT_CATEGORIES.productCategories[0].name,
            productCategoryList[0].name
        )

        // Product
        assertEquals(ProductModel::class.java, products[0].javaClass)
        assertEquals(
            FakeData.PRODUCT_CATEGORIES.productCategories[0].products[0].name,
            products[0].name
        )
    }

    internal class FakeData {
        companion object {
            internal val PRODUCT_FIREBASE_MODEL =
                ProductFirebaseModel(1, "Product 1", 1, 2, "LOCAL")
            internal val PRODUCT_CATEGORY_FIREBASE_MODEL = ProductCategoryFirebaseModel(
                1,
                "Category 1",
                "NOT_AVAILABLE",
                listOf(PRODUCT_FIREBASE_MODEL)
            )
            internal val PRODUCT_CATEGORIES_FIREBASE_MDOEL =
                ProductCategoriesFirebaseModel(listOf(PRODUCT_CATEGORY_FIREBASE_MODEL), "Fake Date")

            val PRODUCT_MODEL = ProductModel(1, "Fake Name", 1, 50, 50, "Fake Origin")
            val PRODUCT_CATEGORY_MODEL = ProductCategoryModel(1, "Fake Name", 1, "Fake Unit")
            val PRODUCT_CATEGORIES_MODEL = ProductCategoriesModel("Fake Date")

            val PRODUCT_CATEGORIES = ProductCategories(
                listOf(
                    ProductCategory(
                        1, "Fake Name",
                        ProductCategory.generateUnitType("KG"),
                        listOf(
                            Product(
                                1,
                                "Fake Name",
                                50,
                                50,
                                Product.generateProductOrigin("LOCAL")
                            )
                        )
                    )
                ),
                "Fake Date"
            )
        }
    }
}
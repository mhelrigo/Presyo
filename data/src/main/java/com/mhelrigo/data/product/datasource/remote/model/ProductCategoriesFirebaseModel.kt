package com.mhelrigo.data.product.datasource.remote.model

import com.mhelrigo.domain.product.entity.ProductCategories
import com.mhelrigo.domain.product.entity.ProductCategory

internal data class ProductCategoriesFirebaseModel(
    val `data`: List<ProductCategoryFirebaseModel>? = null,
    val date: String? = null
) {

    fun transform(productCategoryList: List<ProductCategory>): ProductCategories =
        ProductCategories(productCategoryList, date!!)
}
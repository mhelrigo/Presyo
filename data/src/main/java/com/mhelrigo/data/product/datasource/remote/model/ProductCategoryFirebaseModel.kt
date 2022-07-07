package com.mhelrigo.data.product.datasource.remote.model

import com.mhelrigo.domain.product.entity.Product
import com.mhelrigo.domain.product.entity.ProductCategory

internal data class ProductCategoryFirebaseModel(
    val id: Int? = null,
    val name: String? = null,
    val `unit`: String? = null,
    val `product`: List<ProductFirebaseModel>? = null
) {

    fun transform(products: List<Product>): ProductCategory =
        ProductCategory(id!!, name!!, ProductCategory.generateUnitType(unit!!), products)
}
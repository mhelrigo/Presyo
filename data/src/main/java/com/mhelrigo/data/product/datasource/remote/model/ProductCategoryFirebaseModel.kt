package com.mhelrigo.data.product.datasource.remote.model

import com.mhelrigo.domain.product.entity.ProductCategory

internal data class ProductCategoryFirebaseModel(
    val name: String? = null,
    val `unit`: String? = null,
    val `product`: List<ProductFirebaseModel>? = null
) {
    companion object {
        fun transform(
            name: String? = null,
            `unit`: String? = null,
            `product`: List<ProductFirebaseModel>? = null
        ) = ProductCategory(
            name!!,
            ProductCategory.generateUnitType(unit!!),
            ProductFirebaseModel.transform(product!!)
        )
    }
}
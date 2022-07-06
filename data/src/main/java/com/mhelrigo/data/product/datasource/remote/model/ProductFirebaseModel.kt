package com.mhelrigo.data.product.datasource.remote.model

import com.mhelrigo.domain.product.entity.Product

internal data class ProductFirebaseModel(
    val id: Int? = null,
    val name: String? = null,
    val `current_price`: Long? = null,
    val `previous_price`: Long? = null,
    val `origin`: String? = null
) {
    companion object {
        fun transform(
            id: Int? = null,
            name: String? = null,
            `current_price`: Long? = null,
            `previous_price`: Long? = null,
            `origin`: String? = null
        ) = Product(
            name!!,
            current_price!!,
            previous_price!!,
            Product.generateProductOrigin(origin!!)
        )

        fun transform(productFirebaseModels: List<ProductFirebaseModel>) =
            productFirebaseModels.map {
                transform(it.id, it.name, it.current_price, it.previous_price, it.origin)
            }
    }
}
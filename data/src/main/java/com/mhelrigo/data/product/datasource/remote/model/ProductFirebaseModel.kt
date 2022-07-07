package com.mhelrigo.data.product.datasource.remote.model

import com.mhelrigo.domain.product.entity.Product

internal data class ProductFirebaseModel(
    val id: Int? = null,
    val name: String? = null,
    val `current_price`: Long? = null,
    val `previous_price`: Long? = null,
    val `origin`: String? = null
) {

    fun transform(): Product =
        Product(id!!, name!!, current_price!!, previous_price!!, Product.generateProductOrigin(origin!!))
}
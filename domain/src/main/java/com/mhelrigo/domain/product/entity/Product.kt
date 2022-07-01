package com.mhelrigo.domain.product.entity

data class Product(
    val name: String,
    val currentPrice: Long,
    val previousPrice: Long,
    val productOrigin: ProductOrigin
) {
    companion object {
        fun generateProductOrigin(origin: String) =
            when (origin) {
                ProductOrigin.LOCAL.name -> {
                    ProductOrigin.LOCAL
                }
                ProductOrigin.INTERNATIONAL.name -> {
                    ProductOrigin.INTERNATIONAL
                }
                else -> {
                    ProductOrigin.NOT_AVAILABLE
                }
            }
    }
}
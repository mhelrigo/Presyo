package com.mhelrigo.domain.product.entity

data class Product(
    val name: String,
    val currentPrice: Long,
    val previousPrice: Long,
    val productOrigin: ProductOrigin
) {
}
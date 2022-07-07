package com.mhelrigo.data.product.datasource.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mhelrigo.domain.product.entity.Product

@Entity
data class ProductModel(
    @PrimaryKey var id: Int,
    val name: String,
    val productCategoryId: Int,
    val currentPrice: Long,
    val previousPrice: Long,
    val origin: String
) {
    fun transform(): Product =
        Product(id, name, currentPrice, previousPrice, Product.generateProductOrigin(origin!!))

    companion object {
        fun transform(product: Product, productCategoryId: Int): ProductModel = ProductModel(
            product.id,
            product.name,
            productCategoryId,
            product.currentPrice,
            product.previousPrice,
            product.productOrigin.name
        )
    }
}
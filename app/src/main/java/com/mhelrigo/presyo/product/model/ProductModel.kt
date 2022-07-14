package com.mhelrigo.presyo.product.model

import android.view.View
import com.mhelrigo.domain.product.entity.Product
import com.mhelrigo.domain.product.entity.ProductOrigin
import com.mhelrigo.presyo.R

class ProductModel(
    val id: Int,
    val name: String,
    val currentPrice: Long,
    val previousPrice: Long,
    val productOrigin: String
) {
    fun trendImage() =
        when {
            previousPrice == currentPrice -> {
                R.drawable.ic_flat_trend
            }
            previousPrice < currentPrice -> {
                R.drawable.ic_upward_trend
            }
            else -> {
                R.drawable.ic_downward_trend
            }
        }

    fun displayableCurrentPrice() = if (currentPrice < 0) "N/A" else "current ₱ ${currentPrice.toString()}"

    fun displayablePreviousPrice() = if (previousPrice < 0) "N/A" else "previous ₱ ${previousPrice.toString()}"

    fun originViewVisibility() =
        if (productOrigin == ProductOrigin.INTERNATIONAL.name) View.VISIBLE else View.GONE

    companion object {
        fun transform(product: Product) = ProductModel(
            product.id,
            product.name,
            product.currentPrice,
            product.previousPrice,
            product.productOrigin.name
        )
    }
}
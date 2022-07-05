package com.mhelrigo.presyo.main.model

import android.view.View
import com.mhelrigo.domain.product.entity.ProductOrigin
import com.mhelrigo.presyo.R

internal class ProductModel {
    companion object {
        fun trendImage(previousPrice: Long, currentPrice: Long) =
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

        fun displayablePrice(price: Long) = if (price < 0) "N/A" else price.toString()

        fun originViewVisibility(productOrigin: ProductOrigin) =
            if (productOrigin.name == ProductOrigin.INTERNATIONAL.name) View.VISIBLE else View.GONE
    }
}
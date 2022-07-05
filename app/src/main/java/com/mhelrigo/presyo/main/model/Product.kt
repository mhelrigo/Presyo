package com.mhelrigo.presyo.main.model

import com.mhelrigo.presyo.R

internal class Product {
    companion object {
        fun trendImage(previousPrice: Long, currentPrice: Long) =
            if (previousPrice < currentPrice) R.drawable.ic_upward_trend else R.drawable.ic_downward_trend

        fun displayablePrice(price: Long) = if (price < 0) "N/A" else price.toString()
    }
}
package com.mhelrigo.data.product.datasource.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mhelrigo.domain.product.entity.ProductCategories
import com.mhelrigo.domain.product.entity.ProductCategory

@Entity
data class ProductCategoriesModel(val date: String) {
    @PrimaryKey
    var id: Int = 1

    fun transform(productCategories: List<ProductCategory>): ProductCategories = ProductCategories(productCategories, date)

    companion object {
        fun transform(productCategories: ProductCategories): ProductCategoriesModel =
            ProductCategoriesModel(productCategories.date)
    }
}
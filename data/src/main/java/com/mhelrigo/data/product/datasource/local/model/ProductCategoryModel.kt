package com.mhelrigo.data.product.datasource.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mhelrigo.domain.product.entity.Product
import com.mhelrigo.domain.product.entity.ProductCategory

@Entity
data class ProductCategoryModel(
    @PrimaryKey val id: Int,
    val name: String,
    val productCategoriesId: Int,
    val unit: String
) {
    fun transform(products: List<Product>): ProductCategory =
        ProductCategory(
            id,
            name,
            ProductCategory.generateUnitType(unit),
            products
        )

    companion object {
        fun transform(productCategory: ProductCategory): ProductCategoryModel =
            ProductCategoryModel(
                productCategory.id,
                productCategory.name,
                1,
                productCategory.unitType.name
            )
    }
}
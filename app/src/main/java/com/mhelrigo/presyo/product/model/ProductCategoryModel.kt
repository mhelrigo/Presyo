package com.mhelrigo.presyo.product.model

import com.mhelrigo.domain.product.entity.ProductCategory

data class ProductCategoryModel(
    val id: Int,
    val name: String,
    val unitType: String,
    val products: List<ProductModel>
) {
    companion object {
        fun transform(productCategory: ProductCategory) = ProductCategoryModel(
            productCategory.id,
            productCategory.name,
            productCategory.unitType.name,
            productCategory.products.map {
                ProductModel.transform(it)
            }
        )
    }
}
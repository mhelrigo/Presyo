package com.mhelrigo.data.product.datasource.remote.firebase.model

import com.mhelrigo.domain.product.entity.ProductCategories

internal data class ProductCategoriesFirebaseModel(val `data`: List<ProductCategoryFirebaseModel>? = null) {
    companion object {
        fun transform(productCategoriesFirebaseModel: ProductCategoriesFirebaseModel) = ProductCategories(
            transform(productCategoriesFirebaseModel.data!!))

        fun transform(`data`: List<ProductCategoryFirebaseModel>) = data.map {
            ProductCategoryFirebaseModel.transform(it.name, it.unit, it.product)
        }
    }
}
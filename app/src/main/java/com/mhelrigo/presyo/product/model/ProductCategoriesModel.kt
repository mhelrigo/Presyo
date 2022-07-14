package com.mhelrigo.presyo.product.model

import com.mhelrigo.domain.product.entity.ProductCategories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

data class ProductCategoriesModel(
    val productCategories: List<ProductCategoryModel>,
    val date: String
) {
    companion object {
        // Since there is gonna be a lot of transformation that will be done to this data class
        // and it's child, it will be better off executing in the Default Thread.
        suspend fun transform(productCategories: ProductCategories) = withContext(Dispatchers.Default) {
            ProductCategoriesModel(productCategories.productCategories.map {
                ProductCategoryModel.transform(it)
            }, productCategories.date)
        }
    }
}
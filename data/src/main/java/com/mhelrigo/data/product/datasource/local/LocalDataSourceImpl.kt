package com.mhelrigo.data.product.datasource.local

import com.mhelrigo.data.product.datasource.local.database.ProductDao
import com.mhelrigo.data.product.datasource.local.model.ProductCategoriesModel
import com.mhelrigo.data.product.datasource.local.model.ProductCategoryModel
import com.mhelrigo.data.product.datasource.local.model.ProductModel
import com.mhelrigo.domain.product.entity.Product
import com.mhelrigo.domain.product.entity.ProductCategories
import com.mhelrigo.domain.product.repository.ProductRepository
import junit.runner.Version.id
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(var productDao: ProductDao) :
    ProductRepository.LocalDataSource {
    override suspend fun cacheProducts(productCategories: ProductCategories) {
        productDao.insertProductCategories(ProductCategoriesModel.transform(productCategories))
        productCategories.productCategories.map { productCategory ->
            productDao.insertProductCategory(ProductCategoryModel.transform(productCategory))
            productCategory.products.map {
                productDao.insertProduct(ProductModel.transform(it, productCategory.id))
            }
        }
    }

    override suspend fun getProductCategories(): ProductCategories {
        val getProductCategoryList = productDao.getProductCategories(1)

        return productDao.getProductCategories().transform(getProductCategoryList.map {
            it.transform(productDao.getProducts(it.id).map {
                it.transform()
            })
        })
    }
}
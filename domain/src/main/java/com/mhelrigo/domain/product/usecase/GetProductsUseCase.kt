package com.mhelrigo.domain.product.usecase

import com.mhelrigo.domain.product.repository.DataType
import com.mhelrigo.domain.product.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProductsUseCase @Inject constructor(val productRepository: ProductRepository) {
    suspend operator fun invoke(dataType: DataType) =
        productRepository.getProducts(dataType)
}
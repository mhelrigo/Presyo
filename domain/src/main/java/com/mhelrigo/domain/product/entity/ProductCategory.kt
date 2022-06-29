package com.mhelrigo.domain.product.entity

data class ProductCategory(val name: String, val unitType: UnitType, val products: List<Product>) {
}
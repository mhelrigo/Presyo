package com.mhelrigo.domain.product.entity

data class ProductCategory(val name: String, val unitType: UnitType, val products: List<Product>) {
    companion object {
        fun generateUnitType(unitType: String) = when (unitType) {
            UnitType.KG.name -> {
                UnitType.KG
            }
            else -> {
                UnitType.NOT_AVAILABLE
            }
        }
    }
}
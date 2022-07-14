package com.mhelrigo.presyo.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhelrigo.domain.product.repository.DataType
import com.mhelrigo.domain.product.usecase.GetProductsUseCase
import com.mhelrigo.presyo.product.model.ProductCategoriesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(val getProductsUseCase: GetProductsUseCase) :
    ViewModel() {

    lateinit var loadingProduct: (Boolean) -> Unit
    lateinit var productReceived: (ProductCategoriesModel) -> Unit
    lateinit var errorEncountered: () -> Unit

    /**
     * Sudden configuration changes will result for another call of getProduct, it might happen
     * in the middle of getProduct process.
     *
     * currentlyGettingProduct will stop that from happening.
     * */
    private var currentlyGettingProduct = false
    private fun currentlyGettingProduct(isGettingProduct: Boolean) {
        currentlyGettingProduct = isGettingProduct
    }

    fun getProducts(dataType: DataType) {
        if (currentlyGettingProduct) {
            return
        }

        viewModelScope.launch {
            getProductsUseCase.invoke(dataType)
                .onStart {
                    currentlyGettingProduct(true)
                    loadingProduct.invoke(true)
                }.catch {
                    currentlyGettingProduct(false)
                    loadingProduct.invoke(false)
                    errorEncountered.invoke()
                }.collectLatest {
                    currentlyGettingProduct(false)
                    loadingProduct.invoke(false)
                    productReceived.invoke(ProductCategoriesModel.transform(it))
                }
        }
    }
}
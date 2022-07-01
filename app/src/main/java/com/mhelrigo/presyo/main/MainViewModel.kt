package com.mhelrigo.presyo.main

import androidx.lifecycle.ViewModel
import com.mhelrigo.domain.product.entity.ProductCategories
import com.mhelrigo.domain.product.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(val getProductsUseCase: GetProductsUseCase) : ViewModel(),
    CoroutineScope {

    lateinit var loadingProduct: () -> Unit
    lateinit var productReceived: (ProductCategories) -> Unit
    lateinit var errorEncountered: () -> Unit

    fun getProducts() = launch {
        getProductsUseCase.invoke()
            .onStart {
                loadingProduct.invoke()
            }.catch {
                errorEncountered.invoke()
            }
            .collectLatest {
                productReceived.invoke(it)
                for (productCategory in it.productCategories) {
                    for (product in productCategory.products) {
                        Timber.e("product ${product.name}")
                    }
                }
            }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()
}
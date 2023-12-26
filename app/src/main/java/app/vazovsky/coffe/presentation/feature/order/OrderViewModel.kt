package app.vazovsky.coffe.presentation.feature.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.vazovsky.coffe.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor() : ViewModel() {

    val isOrderPaid = MutableLiveData(false)

    /** Выбранные товары */
    private val _productsLiveData = MutableLiveData<List<Product>>()
    val productsLiveData: LiveData<List<Product>> = _productsLiveData

    fun fullProducts(products: List<Product>) {
        _productsLiveData.postValue(products.filter { product -> product.count > 0 })
    }

    fun selectProduct(product: Product) {
        val products = _productsLiveData.value.orEmpty().map { item ->
            if (item.id == product.id) {
                product.copy(
                    count = product.count.plus(1)
                )
            } else {
                item
            }
        }
        _productsLiveData.value = products
    }

    fun unselectProduct(product: Product) {
        val products = _productsLiveData.value.orEmpty().map { item ->
            if (item.id == product.id && item.count > 0) {
                product.copy(
                    count = product.count.minus(1)
                )
            } else {
                item
            }
        }
        _productsLiveData.value = products
    }

    fun payOrder() {
        isOrderPaid.value = true
    }
}
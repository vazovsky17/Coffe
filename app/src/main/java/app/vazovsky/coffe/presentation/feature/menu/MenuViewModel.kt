package app.vazovsky.coffe.presentation.feature.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.vazovsky.coffe.domain.model.Product
import app.vazovsky.coffe.domain.usecases.GetMenuUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getMenuUseCase: GetMenuUseCase,
) : ViewModel() {

    /** Товары в кофейне */
    private val _productsLiveData = MutableLiveData<List<Product>>()
    val productsLiveData: LiveData<List<Product>> = _productsLiveData

    fun getMenu(id: Int) {
        getMenuUseCase(
            GetMenuUseCase.Params(id)
        ) { result ->
            result.fold { products ->
                _productsLiveData.value = products
                true
            }
        }
    }

    fun selectProduct(product: Product) {
        val products = _productsLiveData.value.orEmpty().map { item ->
            if (item.id == product.id) {
                item.copy(
                    count = item.count.plus(1)
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
                item.copy(
                    count = item.count.minus(1)
                )
            } else {
                item
            }
        }
        _productsLiveData.value = products
    }
}
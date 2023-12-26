package app.vazovsky.coffe.presentation.feature.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.vazovsky.coffe.data.remote.exception.Failure
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

    fun getMenu(id: Int, unauthorizedCallback: () -> Unit) {
        getMenuUseCase(
            GetMenuUseCase.Params(id)
        ) { result ->
            result.fold(
                ifFailure = { failure ->
                    if (failure is Failure.Unauthorized) {
                        unauthorizedCallback()
                    }
                },
                ifSuccess = { products ->
                    _productsLiveData.value = products
                    true
                }
            )
        }
    }

    fun selectProduct(product: Product) {
        val products = _productsLiveData.value.orEmpty().map { item ->
            if (item.id == product.id) {
                product.copy(count = product.count.plus(1))
            } else {
                item
            }
        }
        _productsLiveData.value = products
    }

    fun unselectProduct(product: Product) {
        val products = _productsLiveData.value.orEmpty().map { item ->
            if (item.id == product.id && product.count > 0) {
                product.copy(count = product.count.minus(1))
            } else {
                item
            }
        }
        _productsLiveData.value = products
    }
}
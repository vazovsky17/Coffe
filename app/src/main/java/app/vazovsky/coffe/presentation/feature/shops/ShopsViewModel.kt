package app.vazovsky.coffe.presentation.feature.shops

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.vazovsky.coffe.data.remote.exception.Failure
import app.vazovsky.coffe.domain.base.UseCase
import app.vazovsky.coffe.domain.model.Location
import app.vazovsky.coffe.domain.usecases.GetShopsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShopsViewModel @Inject constructor(
    private val getShopsUseCase: GetShopsUseCase,
) : ViewModel() {

    /** Ближайшие кофейни */
    private val _coffeeShops = MutableLiveData<List<Location>>()
    val coffeeShops: LiveData<List<Location>> = _coffeeShops

    val errorLiveData = MutableLiveData<String>()

    fun getNearestCoffeeShops(unauthorizedCallback: () -> Unit) {
        getShopsUseCase(params = UseCase.None) { result ->
            result.fold(
                ifFailure = { failure ->
                    when (failure) {
                        is Failure.Unauthorized -> {
                            unauthorizedCallback()
                            errorLiveData.value = failure.message
                        }

                        else -> errorLiveData.value = failure.message
                    }
                    if (failure is Failure.Unauthorized) {
                        unauthorizedCallback()
                        errorLiveData.value = failure.message
                    }
                },
                ifSuccess = { locations ->
                    errorLiveData.value = null
                    _coffeeShops.value = locations
                    true
                }
            )
        }
    }
}
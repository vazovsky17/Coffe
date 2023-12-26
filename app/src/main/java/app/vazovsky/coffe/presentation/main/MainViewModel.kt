package app.vazovsky.coffe.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.vazovsky.coffe.domain.base.UseCase
import app.vazovsky.coffe.domain.usecases.ClearAuthDataUseCase
import app.vazovsky.coffe.domain.usecases.GetAuthStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAuthStatusUseCase: GetAuthStatusUseCase,
    private val clearAuthDataUseCase: ClearAuthDataUseCase,
) : ViewModel() {

    /** Данные об авторизации */
    private val _isLoggedInLiveData = MutableLiveData<Boolean>()
    val isLoggedInLiveData: LiveData<Boolean> = _isLoggedInLiveData

    fun getAuthStatus() {
        getAuthStatusUseCase(UseCase.None) { result ->
            result.fold { isLoggedIn ->
                _isLoggedInLiveData.value = isLoggedIn
                true
            }
        }
    }

    fun clearAuthStatus() {
        clearAuthDataUseCase(UseCase.None) { result ->
            result.fold {
                true
            }
        }
    }
}
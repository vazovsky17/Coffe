package app.vazovsky.coffe.presentation.feature.auth.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.coffe.data.model.base.LoadableResult
import app.vazovsky.coffe.domain.model.Token
import app.vazovsky.coffe.domain.usecases.RegisterUseCase
import app.vazovsky.coffe.presentation.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
) : BaseViewModel() {

    val emailLiveData = MutableLiveData("")
    val passwordLiveData = MutableLiveData("")
    val repeatPasswordLiveData = MutableLiveData("")

    /** Результат регистрации */
    private val _registerResultLiveData = MutableLiveData<LoadableResult<Token>>()
    val registerResultLiveData: LiveData<LoadableResult<Token>> = _registerResultLiveData

    fun register(login: String, password: String) {
        _registerResultLiveData.launchLoadData(
            registerUseCase.executeFlow(
                params = RegisterUseCase.Params(
                    login = login,
                    password = password,
                )
            )
        )
    }
}
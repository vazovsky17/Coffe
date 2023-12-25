package app.vazovsky.coffe.presentation.feature.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.vazovsky.coffe.data.model.base.LoadableResult
import app.vazovsky.coffe.domain.model.Token
import app.vazovsky.coffe.domain.usecases.LoginUseCase
import app.vazovsky.coffe.presentation.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel() {
    val emailLiveData = MutableLiveData("")
    val passwordLiveData = MutableLiveData("")

    /** Результат авторизации */
    private val _loginResultLiveData = MutableLiveData<LoadableResult<Token>>()
    val loginResultLiveData: LiveData<LoadableResult<Token>> = _loginResultLiveData

    fun login(login: String, password: String) {
        _loginResultLiveData.launchLoadData(
            loginUseCase.executeFlow(
                params = LoginUseCase.Params(
                    login = login,
                    password = password,
                ),
            ),
        )
    }
}
package app.vazovsky.coffe.presentation.feature.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.vazovsky.coffe.domain.model.Token
import app.vazovsky.coffe.domain.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    val emailLiveData = MutableLiveData("")
    val passwordLiveData = MutableLiveData("")

    /** Результат авторизации */
    private val _tokenLiveData = MutableLiveData<Token>()
    val tokenLiveData: LiveData<Token> = _tokenLiveData

    /** Текст ошибки */
    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    fun login(login: String, password: String) {
        loginUseCase(
            params = LoginUseCase.Params(
                login = login,
                password = password,
            ),
        ) { result ->
            result.fold(
                ifFailure = { error ->
                    /** TODO можно прописать ошибки */
                },
                ifSuccess = { token ->
                    _tokenLiveData.value = token
                    Any()
                }
            )
        }
    }
}
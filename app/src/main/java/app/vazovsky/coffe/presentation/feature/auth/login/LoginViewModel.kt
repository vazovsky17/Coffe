package app.vazovsky.coffe.presentation.feature.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.vazovsky.coffe.domain.model.Token
import app.vazovsky.coffe.domain.usecases.LoginUseCase
import app.vazovsky.coffe.domain.usecases.SaveAuthDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val saveAuthDataUseCase: SaveAuthDataUseCase,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    val emailLiveData = MutableLiveData("")
    val passwordLiveData = MutableLiveData("")

    /** Результат авторизации */
    private val _tokenLiveData = MutableLiveData<Token>()
    val tokenLiveData: LiveData<Token> = _tokenLiveData

    /** Текст ошибки */
    val errorLiveData = MutableLiveData<String>()

    fun login(login: String, password: String) {
        loginUseCase(
            params = LoginUseCase.Params(
                login = login,
                password = password,
            ),
        ) { result ->
            result.fold(
                ifFailure = { failure ->
                    errorLiveData.value = failure.message
                    true
                },
                ifSuccess = { token ->
                    errorLiveData.value = null
                    saveAuthToken(token)
                }
            )
        }
    }

    private fun saveAuthToken(token: Token?) {
        if (token != null) {
            saveAuthDataUseCase(
                params = SaveAuthDataUseCase.Params(token = token)
            ) { result ->
                result.fold {
                    _tokenLiveData.value = token
                    true
                }
            }
        } else {
            errorLiveData.value = "Не удалось авторизоваться"
        }
    }
}
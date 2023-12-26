package app.vazovsky.coffe.presentation.feature.auth.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.vazovsky.coffe.domain.model.Token
import app.vazovsky.coffe.domain.usecases.RegisterUseCase
import app.vazovsky.coffe.domain.usecases.SaveAuthDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val saveAuthDataUseCase: SaveAuthDataUseCase,
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    val emailLiveData = MutableLiveData("")
    val passwordLiveData = MutableLiveData("")
    val repeatPasswordLiveData = MutableLiveData("")

    /** Результат регистрации */
    private val _tokenLiveData = MutableLiveData<Token>()
    val tokenLiveData: LiveData<Token> = _tokenLiveData

    /** Текст ошибки */
    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    fun register(login: String, password: String) {
        registerUseCase(
            params = RegisterUseCase.Params(
                login = login,
                password = password,
            ),
        ) { result ->
            result.fold(
                ifFailure = { error ->
                    /** TODO можно прописать ошибки */
                },
                ifSuccess = { token ->
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
            _errorLiveData.value = "Не удалось зарегистрироваться"
        }
    }
}
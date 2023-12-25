package app.vazovsky.coffe.presentation.feature.auth.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.vazovsky.coffe.domain.model.Token
import app.vazovsky.coffe.domain.usecases.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
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
                    _tokenLiveData.value = token
                    true
                }
            )
        }
    }
}
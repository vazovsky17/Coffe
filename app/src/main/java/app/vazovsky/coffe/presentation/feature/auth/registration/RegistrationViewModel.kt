package app.vazovsky.coffe.presentation.feature.auth.registration

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.vazovsky.coffe.R
import app.vazovsky.coffe.domain.model.Token
import app.vazovsky.coffe.domain.usecases.RegisterUseCase
import app.vazovsky.coffe.domain.usecases.SaveAuthDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
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
    val errorLiveData = MutableLiveData<String>()

    fun register(login: String, password: String) {
        registerUseCase(
            params = RegisterUseCase.Params(
                login = login,
                password = password,
            ),
        ) { result ->
            result.fold(
                ifFailure = {
                    errorLiveData.value = context.getString(R.string.registration_authorized_error)
                    true
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
            errorLiveData.value = context.getString(R.string.registration_authorized_error)
        }
    }

    fun validateAuthData(login: String, password: String, repeatPassword: String) {
        if (password != repeatPassword) {
            errorLiveData.value = context.getString(R.string.registration_password_no_match)
        } else if (login.isNotBlank() or password.isNotBlank() and repeatPassword.isNotBlank()) {
            register(login, password)
        } else {
            errorLiveData.value = context.getString(R.string.registration_empty_data)
        }
    }
}
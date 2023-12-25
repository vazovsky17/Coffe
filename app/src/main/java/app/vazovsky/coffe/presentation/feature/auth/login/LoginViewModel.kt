package app.vazovsky.coffe.presentation.feature.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    val emailLiveData = MutableLiveData("")
    val passwordLiveData = MutableLiveData("")
}
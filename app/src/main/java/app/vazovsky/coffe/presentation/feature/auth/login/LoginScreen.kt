package app.vazovsky.coffe.presentation.feature.auth.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
    navigateToRegistration: () -> Unit,
) {
    val viewModel: LoginViewModel = hiltViewModel()
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("LOGIN SCREEN")
            Button(onClick = navigateToRegistration) {
                Text(text = "Еще не зарегистрированы?")
            }
        }
    }
}
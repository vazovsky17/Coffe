package app.vazovsky.coffe.presentation.feature.auth.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RegistrationScreen(
    navigateToLogin: () -> Unit,
    navigateToShops: () -> Unit,
    navigateToMap: () -> Unit,
    navigateToMenu: () -> Unit,
    navigateToOrder: () -> Unit,
) {
    val viewModel: RegistrationViewModel = hiltViewModel()
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("REGISTRATION SCREEN")
            Button(onClick = navigateToLogin) {
                Text(text = "Уже зарегистрированы?")
            }

            /** Ниже для проверки работы навграфа */
            Button(onClick = navigateToShops) {
                Text(text = "Ближайшие кофейни")
            }
            Button(onClick = navigateToMap) {
                Text(text = "Карта")
            }
            Button(onClick = navigateToMenu) {
                Text(text = "Меню")
            }
            Button(onClick = navigateToOrder) {
                Text(text = "Ваш заказ")
            }
        }
    }
}
package app.vazovsky.coffe.presentation.feature.shops

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ShopsScreen() {
    val viewModel: ShopsViewModel = hiltViewModel()
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("SHOP SCREEN")
        }
    }
}
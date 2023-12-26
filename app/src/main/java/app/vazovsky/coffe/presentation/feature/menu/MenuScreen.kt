package app.vazovsky.coffe.presentation.feature.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import app.vazovsky.coffe.R
import app.vazovsky.coffe.domain.model.Product
import app.vazovsky.coffe.presentation.view.TopBar

@Composable
fun MenuScreen(
    shopId: Int,
    onPayClick: (List<Product>) -> Unit,
    onBackPressed: () -> Unit,
) {
    val viewModel: MenuViewModel = hiltViewModel()
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.menu_topbar_title),
                onBackPressed = onBackPressed,
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onPayClick(listOf()) },
            ) {
                Text(text = stringResource(R.string.menu_pay))
            }
        }
    }
}
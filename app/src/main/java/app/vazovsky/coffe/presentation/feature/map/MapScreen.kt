package app.vazovsky.coffe.presentation.feature.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import app.vazovsky.coffe.R
import app.vazovsky.coffe.presentation.view.TopBar

@Composable
fun MapScreen(
    onBackPressed: () -> Unit,
) {
    val viewModel: MapViewModel = hiltViewModel()
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.map_topbar_title),
                onBackPressed = onBackPressed,
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

        }
    }
}
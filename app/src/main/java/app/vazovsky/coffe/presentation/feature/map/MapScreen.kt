package app.vazovsky.coffe.presentation.feature.map

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
import app.vazovsky.coffe.domain.model.Location
import app.vazovsky.coffe.presentation.view.AppTopBar

@Composable
fun MapScreen(
    shops: List<Location>,
    onShopClick: (Location) -> Unit,
    onBackPressed: () -> Unit,
) {
    val viewModel: MapViewModel = hiltViewModel()
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.map_topbar_title),
                onBackPressed = onBackPressed,
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onShopClick(Location(id = 1, name = "LOL", point = null)) },
            ) {
                Text(text = "Место на карте")
            }
        }
    }
}
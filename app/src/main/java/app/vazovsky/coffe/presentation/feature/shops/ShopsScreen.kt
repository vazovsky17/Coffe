package app.vazovsky.coffe.presentation.feature.shops

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import app.vazovsky.coffe.R
import app.vazovsky.coffe.presentation.view.TopBar

@Composable
fun ShopsScreen(
    onBackPressed: () -> Unit,
) {
    val viewModel: ShopsViewModel = hiltViewModel()
    val shops = viewModel.coffeeShops.observeAsState().value

    SideEffect {
        viewModel.getNearestCoffeeShops()
    }

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.shops_topbar_title),
                onBackPressed = onBackPressed,
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn() {
                items(items = shops.orEmpty()) {

                }
            }
        }
    }
}
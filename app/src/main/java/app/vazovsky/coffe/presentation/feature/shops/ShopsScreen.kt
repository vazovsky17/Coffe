package app.vazovsky.coffe.presentation.feature.shops

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import app.vazovsky.coffe.R
import app.vazovsky.coffe.domain.model.Location
import app.vazovsky.coffe.presentation.view.EmptyContent
import app.vazovsky.coffe.presentation.view.TopBar

@Composable
fun ShopsScreen(
    onBackPressed: () -> Unit,
    onShopClick: (Location) -> Unit,
    onMapClick: (List<Location>) -> Unit,
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
            if (shops.isNullOrEmpty()) {
                EmptyContent()
            } else {
                LazyColumn() {
                    items(items = shops, key = { item -> item.id }) { coffeeShop ->
                        CoffeeShopCard(coffeeShop) {
                            onShopClick(coffeeShop)
                        }
                    }
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onMapClick(shops) },
                ) {
                    Text(text = stringResource(R.string.shops_maps))
                }
            }
        }
    }
}

@Composable
fun CoffeeShopCard(
    coffeeShop: Location,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
    ) {
        Text(text = coffeeShop.name)
        Text(
            text = buildString {
                // TODO пока так
                append(1)
                append(" ")
                append(stringResource(id = R.string.shops_distance_km))
                append(" ")
                append(stringResource(id = R.string.shops_distance_postfix))
            },
        )
    }
}

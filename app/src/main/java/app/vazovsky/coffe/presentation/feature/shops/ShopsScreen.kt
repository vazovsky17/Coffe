package app.vazovsky.coffe.presentation.feature.shops

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.vazovsky.coffe.R
import app.vazovsky.coffe.domain.model.Location
import app.vazovsky.coffe.presentation.ui.theme.Champagne
import app.vazovsky.coffe.presentation.ui.theme.CoyoteBrown
import app.vazovsky.coffe.presentation.ui.theme.PaleTaupe
import app.vazovsky.coffe.presentation.view.AppButton
import app.vazovsky.coffe.presentation.view.EmptyContent
import app.vazovsky.coffe.presentation.view.AppTopBar
import app.vazovsky.coffe.presentation.view.UnauthorizedDialog

@Composable
fun ShopsScreen(
    navigateToAuth: () -> Unit,
    onShopClick: (Location) -> Unit,
    onMapClick: (List<Location>) -> Unit,
) {
    val viewModel: ShopsViewModel = hiltViewModel()
    val shops = viewModel.coffeeShops.observeAsState().value
    val (showUnauthorizedDialog, setShowUnauthorizedDialog) = remember { mutableStateOf(false) }

    SideEffect {
        viewModel.getNearestCoffeeShops {
            setShowUnauthorizedDialog(true)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.shops_topbar_title),
                onBackPressed = navigateToAuth,
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding(), start = 18.dp, end = 18.dp),
        ) {
            if (showUnauthorizedDialog) {
                UnauthorizedDialog {
                    setShowUnauthorizedDialog(false)
                    navigateToAuth()
                }
            }

            // TODO проверить еще на ошибку
            if (shops == null) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (shops.isEmpty()) {
                EmptyContent(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.shops_empty_content),
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 64.dp, top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    items(items = shops, key = { item -> item.id }) { coffeeShop ->
                        CoffeeShopCard(coffeeShop) {
                            onShopClick(coffeeShop)
                        }
                    }
                }

                AppButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 20.dp),
                    text = stringResource(R.string.shops_maps),
                    onClick = { onMapClick(shops) },
                )
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
        colors = CardDefaults.cardColors(containerColor = Champagne),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp, start = 10.dp, end = 10.dp),
            text = coffeeShop.name,
            color = CoyoteBrown,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, start = 10.dp, end = 10.dp, bottom = 15.dp),
            text = buildString {
                // TODO пока так
                append(1)
                append(" ")
                append(stringResource(id = R.string.shops_distance_km))
                append(" ")
                append(stringResource(id = R.string.shops_distance_postfix))
            },
            style = MaterialTheme.typography.bodySmall,
            color = PaleTaupe,
        )
    }
}

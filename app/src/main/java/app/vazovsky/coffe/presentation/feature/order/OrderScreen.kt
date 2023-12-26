package app.vazovsky.coffe.presentation.feature.order

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import app.vazovsky.coffe.R
import app.vazovsky.coffe.domain.model.Product
import app.vazovsky.coffe.presentation.view.TopBar

@Composable
fun OrderScreen(
    products: List<Product>,
    onBackPressed: () -> Unit,
) {
    val viewModel: OrderViewModel = hiltViewModel()
    val selectedProducts = viewModel.productsLiveData.observeAsState().value

    SideEffect {
        viewModel.fullProducts(products)
    }

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.order_topbar_title),
                onBackPressed = onBackPressed,
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (selectedProducts.isNullOrEmpty()) {
                OrderEmptyContent()
            } else {
                LazyColumn() {
                    items(selectedProducts) { product ->
                        ProductCard(
                            product = product,
                            selectProduct = { viewModel.selectProduct(product) },
                            unselectProduct = { viewModel.unselectProduct(product) },
                        )
                    }
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {},
                ) {
                    Text(text = stringResource(R.string.order_confirm))
                }
            }
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    selectProduct: () -> Unit,
    unselectProduct: () -> Unit,
) {
    Card {
        Row {
            Column {
                Text(text = product.name)
                Text(
                    text = buildString {
                        append(product.price)
                        append(" ")
                        append(stringResource(id = R.string.menu_currency))
                    },
                )
            }
            IconButton(onClick = selectProduct) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack, contentDescription = null
                )
            }
            Text(text = product.count.toString())
            IconButton(onClick = unselectProduct) {
                Icon(
                    imageVector = Icons.Filled.Add, contentDescription = null
                )
            }
        }

    }
}

@Composable
fun OrderEmptyContent() {
    Text("Корзина пуста")
}
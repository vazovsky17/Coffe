package app.vazovsky.coffe.presentation.feature.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
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
import app.vazovsky.coffe.presentation.view.EmptyContent
import app.vazovsky.coffe.presentation.view.TopBar

@Composable
fun MenuScreen(
    shopId: Int,
    onPayClick: (List<Product>) -> Unit,
    onBackPressed: () -> Unit,
) {
    val viewModel: MenuViewModel = hiltViewModel()
    val products = viewModel.productsLiveData.observeAsState().value

    SideEffect {
        viewModel.getMenu(shopId)
    }

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.menu_topbar_title),
                onBackPressed = onBackPressed,
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (products.isNullOrEmpty()) {
                EmptyContent()
            } else {
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(count = products.size) { index ->
                        ProductCard(
                            product = products[index],
                            selectProduct = {
                                viewModel.selectProduct(products[index])
                            },
                            unselectProduct = {
                                viewModel.unselectProduct(products[index])
                            }
                        )
                    }
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onPayClick(listOf()) },
                ) {
                    Text(text = stringResource(R.string.menu_pay))
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
    Text(text = "Тут картинка")
    Text(text = product.name)
    Row {
        Text(
            text = buildString {
                append(product.price)
                append(" ")
                append(stringResource(id = R.string.menu_currency))
            },
        )
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

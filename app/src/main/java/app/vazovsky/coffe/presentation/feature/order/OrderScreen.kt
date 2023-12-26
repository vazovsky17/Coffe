package app.vazovsky.coffe.presentation.feature.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.vazovsky.coffe.R
import app.vazovsky.coffe.domain.model.Product
import app.vazovsky.coffe.extensions.orDefault
import app.vazovsky.coffe.presentation.ui.theme.Champagne
import app.vazovsky.coffe.presentation.ui.theme.CoyoteBrown
import app.vazovsky.coffe.presentation.ui.theme.PaleTaupe
import app.vazovsky.coffe.presentation.view.AppButton
import app.vazovsky.coffe.presentation.view.AppTopBar
import app.vazovsky.coffe.presentation.view.EmptyContent

@Composable
fun OrderScreen(
    products: List<Product>,
    onBackPressed: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel: OrderViewModel = hiltViewModel()
    val selectedProducts = viewModel.productsLiveData.observeAsState().value
    val isOrderPaid = viewModel.isOrderPaid.observeAsState().value.orDefault()
    val defaultCardHeight = 70.dp

    SideEffect {
        if (selectedProducts == null) {
            viewModel.fullProducts(products)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.order_topbar_title),
                onBackPressed = onBackPressed,
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding(), start = 18.dp, end = 18.dp),
        ) {
            if (selectedProducts.isNullOrEmpty()) {
                EmptyContent(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.order_empty_content),
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 64.dp, top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    items(selectedProducts, key = { item -> item.id }) { product ->
                        ProductCard(
                            height = defaultCardHeight,
                            product = product,
                            selectProduct = { viewModel.selectProduct(product) },
                            unselectProduct = { viewModel.unselectProduct(product) },
                        )
                    }
                    item {
                        if (isOrderPaid) {
                            val availableHeight =
                                LocalConfiguration.current.screenHeightDp.dp - innerPadding.calculateTopPadding() - innerPadding.calculateBottomPadding() - 80.dp
                            val height = availableHeight - (defaultCardHeight + 6.dp) * selectedProducts.size
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(1F)
                                    .requiredHeight(height),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(1F),
                                    text = stringResource(R.string.order_paid),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 24.sp),
                                    color = CoyoteBrown,
                                )
                            }
                        }
                    }
                }

                AppButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 20.dp),
                    text = stringResource(
                        if (isOrderPaid) {
                            R.string.order_yet_payed
                        } else {
                            R.string.order_confirm
                        }
                    ),
                    enabled = !isOrderPaid,
                    onClick = viewModel::payOrder,
                )
            }
        }
    }
}

@Composable
fun ProductCard(
    height: Dp,
    product: Product,
    selectProduct: () -> Unit,
    unselectProduct: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(height),
        colors = CardDefaults.cardColors(containerColor = Champagne),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(modifier = Modifier.fillMaxHeight()) {
            Column(
                modifier = Modifier
                    .padding(top = 14.dp, start = 10.dp, bottom = 9.dp)
                    .weight(2F),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CoyoteBrown,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = buildString {
                        append(product.price)
                        append(" ")
                        append(stringResource(id = R.string.menu_currency))
                    },
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 16.sp),
                    color = PaleTaupe,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 14.dp, start = 10.dp, bottom = 9.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                IconButton(
                    onClick = unselectProduct
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_minus),
                        contentDescription = null,
                        tint = CoyoteBrown,
                    )
                }
                Text(
                    text = product.count.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    color = CoyoteBrown,
                )
                IconButton(onClick = selectProduct) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_plus),
                        contentDescription = null,
                        tint = CoyoteBrown,
                    )
                }
            }
        }
    }
}

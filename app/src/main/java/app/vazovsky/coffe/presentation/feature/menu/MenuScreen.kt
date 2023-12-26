package app.vazovsky.coffe.presentation.feature.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.vazovsky.coffe.R
import app.vazovsky.coffe.domain.model.Product
import app.vazovsky.coffe.presentation.ui.theme.Champagne
import app.vazovsky.coffe.presentation.ui.theme.CoyoteBrown
import app.vazovsky.coffe.presentation.ui.theme.PaleTaupe
import app.vazovsky.coffe.presentation.view.AppButton
import app.vazovsky.coffe.presentation.view.AppTopBar
import app.vazovsky.coffe.presentation.view.EmptyContent
import app.vazovsky.coffe.presentation.view.UnauthorizedDialog
import coil.compose.rememberAsyncImagePainter

@Composable
fun MenuScreen(
    shopId: Int,
    onPayClick: (List<Product>) -> Unit,
    onBackPressed: () -> Unit,
    navigateToAuth: () -> Unit,
) {
    val viewModel: MenuViewModel = hiltViewModel()
    val products = viewModel.productsLiveData.observeAsState().value
    val error = viewModel.errorLiveData.observeAsState().value
    val (showUnauthorizedDialog, setShowUnauthorizedDialog) = remember { mutableStateOf(false) }

    SideEffect {
        if (products == null) {
            viewModel.getMenu(shopId) {
                setShowUnauthorizedDialog(true)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.menu_topbar_title),
                onBackPressed = onBackPressed,
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding(), start = 16.dp, end = 16.dp)
        ) {
            if (showUnauthorizedDialog) {
                UnauthorizedDialog {
                    setShowUnauthorizedDialog(false)
                    navigateToAuth()
                }
            }

            if (products == null && error == null) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (products.isNullOrEmpty()) {
                EmptyContent(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.menu_empty_content),
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(13.dp),
                    horizontalArrangement = Arrangement.spacedBy(13.dp),
                    contentPadding = PaddingValues(bottom = 64.dp, top = 16.dp),
                ) {
                    items(items = products, key = { item -> item.id }) { product ->
                        ProductCard(
                            product = product,
                            selectProduct = {
                                viewModel.selectProduct(product)
                            },
                            unselectProduct = {
                                viewModel.unselectProduct(product)
                            },
                        )
                    }
                }

                AppButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 20.dp),
                    text = stringResource(R.string.menu_pay),
                    onClick = { onPayClick(products) },
                )
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
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = product.imageURL),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.2F)
                .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)),
            contentScale = ContentScale.Crop,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 11.dp, end = 11.dp),
            text = product.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleSmall,
            color = PaleTaupe,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 11.dp, end = 11.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buildString {
                    append(product.price)
                    append(" ")
                    append(stringResource(id = R.string.menu_currency))
                },
                style = MaterialTheme.typography.bodyMedium,
                color = CoyoteBrown,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = unselectProduct) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_minus),
                        contentDescription = null,
                        tint = Champagne,
                    )
                }
                Text(
                    modifier = Modifier.fillMaxHeight(),
                    text = product.count.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = CoyoteBrown,
                )
                IconButton(onClick = selectProduct) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_plus),
                        contentDescription = null,
                        tint = Champagne,
                    )
                }
            }
        }
    }
}

package app.vazovsky.coffe.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import app.vazovsky.coffe.domain.model.Location
import app.vazovsky.coffe.domain.model.Product
import app.vazovsky.coffe.presentation.feature.auth.login.LoginScreen
import app.vazovsky.coffe.presentation.feature.auth.registration.RegistrationScreen
import app.vazovsky.coffe.presentation.feature.map.MapScreen
import app.vazovsky.coffe.presentation.feature.menu.MenuScreen
import app.vazovsky.coffe.presentation.feature.order.OrderScreen
import app.vazovsky.coffe.presentation.feature.shops.ShopsScreen
import app.vazovsky.coffe.presentation.main.MainViewModel
import app.vazovsky.coffe.presentation.navigation.Args.ARG_PRODUCTS
import app.vazovsky.coffe.presentation.navigation.Args.ARG_SHOPS
import app.vazovsky.coffe.presentation.navigation.Args.ARG_SHOP_ID

@Composable
fun MainNavGraph() {
    val viewModel: MainViewModel = hiltViewModel()
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }
    val isLoggedIn = viewModel.isLoggedInLiveData.observeAsState().value

    SideEffect {
        viewModel.getAuthStatus()
    }

    val startDestination = when (isLoggedIn) {
        true -> Graph.Main.route
        false -> Graph.Auth.route
        null -> Graph.Splash.route
    }

    Scaffold(modifier = Modifier.fillMaxSize(), containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = startDestination,
        ) {
            composable(Graph.Splash.route) {}

            navigation(route = Graph.AUTH, startDestination = AuthScreen.Registration.route) {

                composable(AuthScreen.Registration.route) {
                    RegistrationScreen(
                        navigateToLogin = navigationActions::navigateToLogin,
                        navigateToMain = navigationActions::navigateToMain,
                    )
                }

                composable(AuthScreen.Login.route) {
                    LoginScreen(
                        navigateToRegistration = navigationActions::navigateUp,
                        navigateToMain = navigationActions::navigateToMain,
                    )
                }
            }

            navigation(route = Graph.MAIN, startDestination = MainScreen.Shops.route) {

                composable(MainScreen.Shops.route) {
                    ShopsScreen(
                        onBackPressed = {
                            viewModel.clearAuthStatus()
                            navigationActions.navigateToAuth()
                        },
                        onShopClick = { location ->
                            navigationActions.navigateToMenu(location.id)
                        },
                        onMapClick = { shops ->
                            navigationActions.navigateToMap(shops)
                        }
                    )
                }

                composable(MainScreen.Map.route) {
                    navController.previousBackStackEntry?.savedStateHandle?.apply {
                        val shops = get<List<Location>>(ARG_SHOPS)
                        if (shops != null) {
                            MapScreen(
                                shops = shops,
                                onShopClick = { location ->
                                    navigationActions.navigateToMenu(location.id)
                                },
                                onBackPressed = navigationActions::navigateUp,
                            )
                        }
                    }
                }

                composable(MainScreen.Menu.route) {
                    navController.previousBackStackEntry?.savedStateHandle?.apply {
                        val shopId = get<Int>(ARG_SHOP_ID)
                        if (shopId != null) {
                            MenuScreen(
                                shopId = shopId,
                                onPayClick = { products ->
                                    navigationActions.navigateToOrder(products)
                                },
                                onBackPressed = navigationActions::navigateUp,
                            )
                        }
                    }
                }

                composable(MainScreen.Order.route) {
                    navController.previousBackStackEntry?.savedStateHandle?.apply {
                        val products = get<List<Product>>(ARG_PRODUCTS)
                        if (products != null) {
                            OrderScreen(
                                onBackPressed = navigationActions::navigateUp,
                            )
                        }
                    }
                }
            }
        }
    }
}

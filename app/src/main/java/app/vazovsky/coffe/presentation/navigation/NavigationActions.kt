package app.vazovsky.coffe.presentation.navigation

import androidx.compose.runtime.Stable
import androidx.navigation.NavController
import app.vazovsky.coffe.domain.model.Location
import app.vazovsky.coffe.domain.model.Product
import app.vazovsky.coffe.presentation.navigation.Args.ARG_PRODUCTS
import app.vazovsky.coffe.presentation.navigation.Args.ARG_SHOPS
import app.vazovsky.coffe.presentation.navigation.Args.ARG_SHOP_ID

@Stable
class NavigationActions(private val navController: NavController) {

    fun navigateUp() {
        navController.navigateUp()
    }

    fun popBackStack() {
        navController.popBackStack()
    }

    fun navigateToAuth() = navController.navigate(Graph.Auth.route) {
        popUpTo(navController.graph.id)
    }

    fun navigateToMain() = navController.navigate(Graph.Main.route) {
        popUpTo(navController.graph.id)
    }


    fun navigateToLogin() = navController.navigate(AuthScreen.Login.route)

    fun navigateToRegistration() = navController.navigate(AuthScreen.Registration.route)

    fun navigateToShops() = navController.navigate(MainScreen.Shops.route)

    fun navigateToMap(shops: List<Location>) {
        navController.currentBackStackEntry?.savedStateHandle?.apply {
            this[ARG_SHOPS] = shops
        }

        navController.navigate(MainScreen.Map.route)
    }

    fun navigateToMenu(id: Int) {
        navController.currentBackStackEntry?.savedStateHandle?.apply {
            this[ARG_SHOP_ID] = id
        }
        navController.navigate(MainScreen.Menu.route)
    }

    fun navigateToOrder(products: List<Product>) {
        navController.currentBackStackEntry?.savedStateHandle?.apply {
            this[ARG_PRODUCTS] = products
        }

        navController.navigate(MainScreen.Order.route)
    }
}

object Args {
    const val ARG_SHOP_ID = "shop_id"
    const val ARG_SHOPS = "shops"
    const val ARG_PRODUCTS = "products"
}
package app.vazovsky.coffe.presentation.navigation

import androidx.compose.runtime.Stable
import androidx.navigation.NavController

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

    fun navigateToMap() = navController.navigate(MainScreen.Map.route)

    fun navigateToMenu() = navController.navigate(MainScreen.Menu.route)

    fun navigateToOrder() = navController.navigate(MainScreen.Order.route)
}
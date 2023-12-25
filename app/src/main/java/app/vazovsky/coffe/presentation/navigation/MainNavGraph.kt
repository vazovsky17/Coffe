package app.vazovsky.coffe.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import app.vazovsky.coffe.presentation.feature.auth.login.LoginScreen
import app.vazovsky.coffe.presentation.feature.auth.registration.RegistrationScreen
import app.vazovsky.coffe.presentation.feature.map.MapScreen
import app.vazovsky.coffe.presentation.feature.menu.MenuScreen
import app.vazovsky.coffe.presentation.feature.order.OrderScreen
import app.vazovsky.coffe.presentation.feature.shops.ShopsScreen
import app.vazovsky.coffe.presentation.main.MainViewModel

@Composable
fun MainNavGraph() {
    val viewModel: MainViewModel = hiltViewModel()
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }

    val showAuth = true

    val startDestination = if (showAuth) {
        Graph.Auth.route
    } else {
        Graph.Main.route
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(modifier = Modifier.fillMaxSize(), containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = startDestination,
        ) {

            navigation(route = Graph.AUTH, startDestination = AuthScreen.Registration.route) {

                composable(AuthScreen.Registration.route) {
                    RegistrationScreen(
                        navigateToLogin = navigationActions::navigateToLogin,
                        onConfirmClick = navigationActions::navigateToMain,
                    )
                }

                composable(AuthScreen.Login.route) {
                    LoginScreen(
                        navigateToRegistration = navigationActions::navigateUp,
                        onConfirmClick = navigationActions::navigateToMain,
                    )
                }
            }

            navigation(route = Graph.MAIN, startDestination = MainScreen.Shops.route) {

                composable(MainScreen.Shops.route) {
                    ShopsScreen(
                        onBackPressed = navigationActions::navigateUp,
                    )
                }

                composable(MainScreen.Map.route) {
                    MapScreen(
                        onBackPressed = navigationActions::navigateUp,
                    )
                }

                composable(MainScreen.Menu.route) {
                    MenuScreen(
                        onBackPressed = navigationActions::navigateUp,
                    )
                }

                composable(MainScreen.Order.route) {
                    OrderScreen(
                        onBackPressed = navigationActions::navigateUp,
                    )
                }
            }
        }
    }
}

package app.vazovsky.coffe.presentation.navigation

sealed class Graph(val route: String) {

    data object Splash : Graph(SPLASH)
    data object Auth : Graph(AUTH)
    data object Main : Graph(MAIN)

    companion object {
        const val SPLASH = "splash"
        const val AUTH = "auth"
        const val MAIN = "main"
    }
}

sealed class AuthScreen(val route: String) {

    data object Login : AuthScreen(LOGIN)
    data object Registration : AuthScreen(REGISTRATION)

    companion object {
        const val LOGIN = "login"
        const val REGISTRATION = "registration"
    }
}

sealed class MainScreen(val route: String) {

    data object Shops : MainScreen(SHOPS)
    data object Map : MainScreen(MAP)
    data object Menu : MainScreen(MENU)
    data object Order : MainScreen(ORDER)

    companion object {
        const val SHOPS = "shops"
        const val MAP = "map"
        const val MENU = "menu"
        const val ORDER = "order"
    }
}
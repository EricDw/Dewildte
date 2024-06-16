package presentation.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import presentation.navigation.Route

const val BASE_HOME_ROUTE: String = "home/"

object HomeRoute : Route

fun NavGraphBuilder.home() {
    composable(route = BASE_HOME_ROUTE) {
        HomeScreenController()
    }
}
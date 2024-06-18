package presentation.home

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import presentation.navigation.Route

const val HOME_NAVIGATION_GRAPH_ROUTE: String = "home-graph"
private const val HOME_ROUTE = "home"
private const val START_DESTINATION = HOME_ROUTE

object HomeRoute : Route

fun NavGraphBuilder.homeNavigationGraph(
    onLaunch: () -> Unit = {},
) {
    navigation(
        startDestination = START_DESTINATION,
        route = HOME_NAVIGATION_GRAPH_ROUTE
    ) {
        composable(
            route = HOME_ROUTE
        ) {
            HomeScreenController()

            LaunchedEffect(onLaunch) {
                onLaunch()
            }
        }

        onLaunch()
    }
}
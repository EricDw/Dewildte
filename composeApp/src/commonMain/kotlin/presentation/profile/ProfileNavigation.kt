package presentation.profile

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import presentation.navigation.Route

const val PROFILE_NAVIGATION_GRAPH_ROUTE: String = "profile-graph"
private const val PROFILE_ROUTE: String = "profile"
private const val START_DESTINATION: String = PROFILE_ROUTE

object ProfileRoute : Route

fun NavGraphBuilder.profileNavigationGraph(
    onLaunch: () -> Unit = {},
) {
    navigation(
        startDestination = START_DESTINATION,
        route = PROFILE_NAVIGATION_GRAPH_ROUTE
    ) {
        composable(
            route = PROFILE_ROUTE
        ) {
            ProfileScreenController()

            LaunchedEffect(onLaunch) {
                onLaunch()
            }
        }
    }
}
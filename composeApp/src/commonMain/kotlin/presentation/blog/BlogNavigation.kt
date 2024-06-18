package presentation.blog

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import presentation.navigation.Route

const val BLOG_NAVIGATION_GRAPH_ROUTE: String = "blog-graph"
private const val BLOG_ROUTE = "todo"
private const val START_DESTINATION = BLOG_ROUTE

object BlogRoute : Route

fun NavGraphBuilder.blogNavigationGraph(
    onLaunch: () -> Unit = {},
) {
    navigation(
        startDestination = START_DESTINATION,
        route = BLOG_NAVIGATION_GRAPH_ROUTE
    ) {
        composable(
            route = BLOG_ROUTE
        ) {
            BlogScreenController()

            LaunchedEffect(onLaunch) {
                onLaunch()
            }
        }
    }
}
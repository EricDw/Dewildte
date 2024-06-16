package presentation.blog

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import presentation.navigation.Route

const val BASE_BLOG_ROUTE: String = "blog/"

object BlogRoute : Route

fun NavGraphBuilder.blog() {
    composable(
        route = BASE_BLOG_ROUTE
    ) {
        BlogScreenController()
    }
}
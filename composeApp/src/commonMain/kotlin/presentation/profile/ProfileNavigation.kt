package presentation.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import presentation.navigation.Route

const val BASE_PROFILE_ROUTE: String = "profile/"

object ProfileRoute : Route

fun NavGraphBuilder.profile() {
    composable(route = BASE_PROFILE_ROUTE) {
        ProfileScreenController()
    }
}
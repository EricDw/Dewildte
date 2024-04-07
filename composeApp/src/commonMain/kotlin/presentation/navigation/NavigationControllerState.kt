package presentation.navigation

import androidx.compose.runtime.*
import presentation.home.HomeRoute

@Stable
class NavigationControllerState(
	initialRoute: Route? = null,
) {

	private var _selectedRoute: Route? by mutableStateOf(initialRoute)
	val selectedRoute: Route?
		get() = _selectedRoute
	
	fun navigateToRoute(route: Route) {
		_selectedRoute = route
	}

}

@Composable
fun rememberNavigationControllerState(
	initialRoute: Route? = null,
): NavigationControllerState {
	return remember(
		initialRoute,
	) {
		NavigationControllerState(
			initialRoute = initialRoute,
		)
	}
}
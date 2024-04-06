package presentation.navigation

import androidx.compose.runtime.*
import presentation.blog.BASE_BLOG_ROUTE
import presentation.profile.BASE_PROFILE_ROUTE

@Stable
class NavigationControllerState(
	initialRoute: String? = BASE_PROFILE_ROUTE,
) {

	private var _selectedRoute: String? by mutableStateOf(initialRoute)
	
	val selectedRoute: String?
		get() = _selectedRoute
	
	val isProfileSelected: Boolean
		get() = _selectedRoute?.contains(BASE_PROFILE_ROUTE) ?: false
	
	val isBlogSelected: Boolean
		get() = _selectedRoute?.contains(BASE_BLOG_ROUTE) ?: false
	
	fun navigateToProfile() {
		_selectedRoute = BASE_PROFILE_ROUTE
	}
	
	fun navigateToBlog() {
		_selectedRoute = BASE_BLOG_ROUTE
	}
}

@Composable
fun rememberNavigationControllerState(
	initialRoute: String? = BASE_PROFILE_ROUTE,
): NavigationControllerState {
	return remember(
		initialRoute,
	) {
		NavigationControllerState(
			initialRoute = initialRoute,
		)
	}
}
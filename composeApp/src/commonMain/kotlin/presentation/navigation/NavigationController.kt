package presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import presentation.blog.BASE_BLOG_ROUTE
import presentation.blog.BlogRoute
import presentation.blog.BlogScreenController
import presentation.home.HomeRoute
import presentation.home.HomeScreenController
import presentation.profile.ProfileRoute
import presentation.profile.ProfileScreenController

@Composable
fun NavigationContoller(
	state: NavigationControllerState = rememberNavigationControllerState(),
	modifer: Modifier = Modifier,
) {
	
	AnimatedVisibility(
		visible = state.selectedRoute is HomeRoute,
		modifier = modifer, 
	) {
		HomeScreenController()
	}

	AnimatedVisibility(
		visible = state.selectedRoute is ProfileRoute,
		modifier = modifer,
	) {
		ProfileScreenController()
	}

	AnimatedVisibility(
		visible = state.selectedRoute is BlogRoute,
		modifier = modifer, 
	) {
		BlogScreenController()
	}
	
}

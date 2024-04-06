package presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import presentation.blog.BASE_BLOG_ROUTE
import presentation.blog.BlogScreenController
import presentation.profile.BASE_PROFILE_ROUTE
import presentation.profile.ProfileScreenController

@Composable
fun NavigationContoller(
	state: NavigationControllerState = rememberNavigationControllerState(),
	modifer: Modifier = Modifier,
) {
	
	AnimatedVisibility(
		visible = state.selectedRoute?.contains(BASE_PROFILE_ROUTE) ?: false,
		modifier = modifer, 
	) {
		ProfileScreenController()
	}
	
	AnimatedVisibility(
		visible = state.selectedRoute?.contains(BASE_BLOG_ROUTE) ?: false,
		modifier = modifer, 
	) {
		BlogScreenController()
	}
	
}

@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)

package presentation

import BlogIcon
import HomeIcon
import MenuIcon
import ProfileIcon
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import dewildte.composeapp.generated.resources.*
import dewildte.composeapp.generated.resources.Res
import dewildte.composeapp.generated.resources.label_blog
import dewildte.composeapp.generated.resources.label_my_name
import dewildte.composeapp.generated.resources.label_profile
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.blog.BlogRoute
import presentation.home.HomeRoute
import presentation.navigation.NavigationContoller
import presentation.navigation.rememberNavigationControllerState
import presentation.profile.ProfileRoute

@Composable
@Preview
fun WebApp() {
	val scope = rememberCoroutineScope()
	val drawerState = rememberDrawerState(DrawerValue.Closed)
	val navigationControllerState = rememberNavigationControllerState(
		initialRoute = HomeRoute,
	)

	val rootRoutes = listOf(
		HomeRoute,
		ProfileRoute,
		BlogRoute,
	)

	ModalNavigationDrawer(
		drawerState = drawerState,
		drawerContent = {
			ModalDrawerSheet {

				rootRoutes.forEach { route ->

					NavigationDrawerItem(
						selected = navigationControllerState.selectedRoute == route,
						onClick = {
							scope.launch {
								drawerState.close()
								navigationControllerState.navigateToRoute(route)
							}
						},
						icon = {
							when (route) {
								is HomeRoute -> {
									HomeIcon()
								}

								is ProfileRoute -> {
									ProfileIcon()
								}

								is BlogRoute -> {
									BlogIcon()
								}
							}
						},
						label = {
							val resource = when (route) {
								is HomeRoute -> {
									Res.string.label_home
								}

								is ProfileRoute -> {
									Res.string.label_profile
								}

								is BlogRoute -> {
									Res.string.label_blog
								}

								else -> {
									null
								}
							}

							resource?.let {
								Text(text = stringResource(it))
							}

						}
					)
				}
			}
		},
	) {
		Row(
			modifier = Modifier.fillMaxSize(),
		) {

			NavigationRail(
				header = {
					IconButton(
						onClick = {
							scope.launch {
								drawerState.open()
							}
						}
					) {
						MenuIcon()
					}
				},
			) {

				rootRoutes.forEach { route ->

					NavigationRailItem(
						selected = navigationControllerState.selectedRoute == route,
						onClick = {
							scope.launch {
								drawerState.close()
								navigationControllerState.navigateToRoute(route)
							}
						},
						icon = {
							when (route) {
								is HomeRoute -> {
									HomeIcon()
								}

								is ProfileRoute -> {
									ProfileIcon()
								}

								is BlogRoute -> {
									BlogIcon()
								}
							}
						},
						label = {
							val resource = when (route) {
								is HomeRoute -> {
									Res.string.label_home
								}

								is ProfileRoute -> {
									Res.string.label_profile
								}

								is BlogRoute -> {
									Res.string.label_blog
								}

								else -> {
									null
								}
							}

							resource?.let {
								Text(text = stringResource(it))
							}

						}
					)
				}
			}

			Scaffold(
				topBar = {
					CenterAlignedTopAppBar(
						title = {
							Text(text = stringResource(Res.string.label_my_name))
						}
					)
				}
			) { contentPadding ->

				val contentModifier = Modifier
					.fillMaxSize()
					.padding(contentPadding)

				NavigationContoller(
					state = navigationControllerState,
					modifer = contentModifier,
				)
			}
		}
	}
}
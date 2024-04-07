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

	ModalNavigationDrawer(
		drawerState = drawerState,
		drawerContent = {
			ModalDrawerSheet {

				NavigationDrawerItem(
					selected = navigationControllerState.selectedRoute is HomeRoute,
					onClick = {
						scope.launch {
							drawerState.close()
							navigationControllerState.navigateToRoute(HomeRoute)
						}
					},
					icon = {
						ProfileIcon()
					},
					label = {
						Text(text = stringResource(Res.string.label_home))
					}
				)

				NavigationDrawerItem(
					selected = navigationControllerState.selectedRoute is ProfileRoute,
					onClick = {
						scope.launch {
							drawerState.close()
							navigationControllerState.navigateToRoute(ProfileRoute)
						}
					},
					icon = {
						ProfileIcon()
					},
					label = {
						Text(text = stringResource(Res.string.label_profile))
					}
				)

				NavigationDrawerItem(
					selected = navigationControllerState.selectedRoute is BlogRoute,
					onClick = {
						scope.launch {
							drawerState.close()
							navigationControllerState.navigateToRoute(BlogRoute)
						}
					},
					icon = {
						BlogIcon()
					},
					label = {
						Text(text = stringResource(Res.string.label_blog))
					}
				)

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

				NavigationRailItem(
					selected = navigationControllerState.selectedRoute is HomeRoute,
					onClick = {
						navigationControllerState.navigateToRoute(HomeRoute)
					},
					icon = {
						HomeIcon()
					},
					label = {
						Text(text = stringResource(Res.string.label_home))
					}
				)

				NavigationRailItem(
					selected = navigationControllerState.selectedRoute is ProfileRoute,
					onClick = {
						navigationControllerState.navigateToRoute(ProfileRoute)
					},
					icon = {
						ProfileIcon()
					},
					label = {
						Text(text = stringResource(Res.string.label_profile))
					}
				)

				NavigationRailItem(
					selected = navigationControllerState.selectedRoute is BlogRoute,
					onClick = {
						navigationControllerState.navigateToRoute(BlogRoute)
					},
					icon = {
						BlogIcon()
					},
					label = {
						Text(text = stringResource(Res.string.label_blog))
					}
				)
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
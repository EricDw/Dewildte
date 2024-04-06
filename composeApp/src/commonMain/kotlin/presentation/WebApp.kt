@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)

package presentation

import BlogIcon
import MenuIcon
import ProfileIcon
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import dewildte.composeapp.generated.resources.Res
import dewildte.composeapp.generated.resources.label_blog
import dewildte.composeapp.generated.resources.label_my_name
import dewildte.composeapp.generated.resources.label_profile
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.navigation.NavigationContoller
import presentation.navigation.rememberNavigationControllerState

@Composable
@Preview
fun WebApp() {
	val scope = rememberCoroutineScope()
	val drawerState = rememberDrawerState(DrawerValue.Closed)
	val navigationControllerState = rememberNavigationControllerState()

	ModalNavigationDrawer(
		drawerState = drawerState,
		drawerContent = {
			ModalDrawerSheet {

				NavigationDrawerItem(
					selected = navigationControllerState.isProfileSelected,
					onClick = {
						scope.launch {
							drawerState.close()
							navigationControllerState.navigateToProfile()
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
					selected = navigationControllerState.isBlogSelected,
					onClick = {
						scope.launch {
							drawerState.close()
							navigationControllerState.navigateToBlog()
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
					selected = navigationControllerState.isProfileSelected,
					onClick = navigationControllerState::navigateToProfile,
					icon = {
						ProfileIcon()
					},
					label = {
						Text(text = stringResource(Res.string.label_profile))
					}
				)

				NavigationRailItem(
					selected = navigationControllerState.isBlogSelected,
					onClick = navigationControllerState::navigateToBlog,
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
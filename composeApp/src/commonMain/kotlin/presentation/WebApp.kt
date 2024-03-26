@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)

package presentation

import EditIcon
import MenuIcon
import ProfileIcon
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dewildte.composeapp.generated.resources.Res
import dewildte.composeapp.generated.resources.label_my_name
import dewildte.composeapp.generated.resources.label_profile
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun WebApp() {
	val scope = rememberCoroutineScope()
	val drawerState = rememberDrawerState(DrawerValue.Closed)

	ModalNavigationDrawer(
		drawerState = drawerState,
		drawerContent = {
			ModalDrawerSheet {
				Text("Drawer Sheet")
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

					FloatingActionButton(
						onClick = {
							// TODO: Implement
						},
					) {
						EditIcon()
					}
				},
			) {

				NavigationRailItem(
					selected = true,
					onClick = {},
					icon = {
						ProfileIcon()
					},
					label = {
						Text(text = stringResource(Res.string.label_profile))
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

				Box(
					modifier = contentModifier,
					contentAlignment = Alignment.Center,
				) {
					Text(text = "Hello, World")
				}

			}


		}

	}

}
@file:OptIn(ExperimentalMaterial3Api::class)

package presentation

import BlogIcon
import HomeIcon
import ProfileIcon
import SearchIcon
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import design.space.VerticalSpacer100
import dewildte.composeapp.generated.resources.Res
import dewildte.composeapp.generated.resources.label_blog
import dewildte.composeapp.generated.resources.label_home
import dewildte.composeapp.generated.resources.label_profile
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.blog.BLOG_NAVIGATION_GRAPH_ROUTE
import presentation.blog.BlogRoute
import presentation.blog.blogNavigationGraph
import presentation.home.HOME_NAVIGATION_GRAPH_ROUTE
import presentation.home.HomeRoute
import presentation.home.homeNavigationGraph
import presentation.navigation.Route
import presentation.profile.PROFILE_NAVIGATION_GRAPH_ROUTE
import presentation.profile.ProfileRoute
import presentation.profile.profileNavigationGraph

@Composable
@Preview
fun WebApp() {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val navController = rememberNavController()

    val rootRoutes = listOf(
        HomeRoute,
        ProfileRoute,
        BlogRoute,
    )

    var selectedRoute: Route by remember {
        mutableStateOf(HomeRoute)
    }

    fun performRootNavigation(
        route: Route,
    ) {
        scope.launch {
            drawerState.close()

            when (route) {
                is HomeRoute -> {
                    navController.navigate(
                        route = HOME_NAVIGATION_GRAPH_ROUTE
                    )
                }

                is ProfileRoute -> {
                    navController.navigate(
                        route = PROFILE_NAVIGATION_GRAPH_ROUTE
                    )
                }

                is BlogRoute -> {
                    navController.navigate(
                        route = BLOG_NAVIGATION_GRAPH_ROUTE
                    )
                }
            }
        }
    }


    Row(
        modifier = Modifier.fillMaxSize(),
    ) {

        NavigationRail(
            header = {
                VerticalSpacer100()

                FloatingActionButton(
                    onClick = {
                        // TODO: Implement search
                        scope.launch {
                            drawerState.open()
                        }
                    }
                ) {
                    SearchIcon()
                }
            },
        ) {

            rootRoutes.forEach { route ->

                NavigationRailItem(
                    selected = selectedRoute == route,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            performRootNavigation(route)
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

        Scaffold { contentPadding ->

            ModalNavigationDrawer(
                modifier = Modifier.fillMaxSize().padding(contentPadding),
                drawerState = drawerState,
                gesturesEnabled = drawerState.isOpen,
                scrimColor = Color.Transparent,
                drawerContent = {

                    ModalDrawerSheet {

                    Text(text = "TODO", modifier = Modifier.fillMaxHeight(), textAlign = TextAlign.Center)

                        /*rootRoutes.forEach { route ->

                            NavigationDrawerItem(
                                selected = selectedRoute == route,
                                onClick = {
                                    performRootNavigation(route)
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
                        }*/
                    }
                },
            ) {

                val contentModifier = Modifier
                    .fillMaxSize()

                NavHost(
                    navController = navController,
                    modifier = contentModifier,
                    startDestination = HOME_NAVIGATION_GRAPH_ROUTE,
                ) {
                    homeNavigationGraph()
                    profileNavigationGraph()
                    blogNavigationGraph()
                }

            }
        }

    }

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.parent?.route) {
                HOME_NAVIGATION_GRAPH_ROUTE -> {
                    selectedRoute = HomeRoute
                }

                PROFILE_NAVIGATION_GRAPH_ROUTE -> {
                    selectedRoute = ProfileRoute
                }

                BLOG_NAVIGATION_GRAPH_ROUTE -> {
                    selectedRoute = BlogRoute
                }
            }
        }
    }

}
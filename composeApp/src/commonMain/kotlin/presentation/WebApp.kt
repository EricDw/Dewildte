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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dewildte.composeapp.generated.resources.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.blog.BASE_BLOG_ROUTE
import presentation.blog.BlogRoute
import presentation.blog.blog
import presentation.home.BASE_HOME_ROUTE
import presentation.home.HomeRoute
import presentation.home.home
import presentation.navigation.Route
import presentation.profile.BASE_PROFILE_ROUTE
import presentation.profile.ProfileRoute
import presentation.profile.profile

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
                is HomeRoute    -> {
                    navController.navigate(
                        route = BASE_HOME_ROUTE
                    )
                }

                is ProfileRoute -> {
                    navController.navigate(
                        route = BASE_PROFILE_ROUTE
                    )
                }

                is BlogRoute    -> {
                    navController.navigate(
                        route = BASE_BLOG_ROUTE
                    )
                }
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

                rootRoutes.forEach { route ->

                    NavigationDrawerItem(
                        selected = selectedRoute == route,
                        onClick = {
                            performRootNavigation(route)
                        },
                        icon = {
                            when (route) {
                                is HomeRoute    -> {
                                    HomeIcon()
                                }

                                is ProfileRoute -> {
                                    ProfileIcon()
                                }

                                is BlogRoute    -> {
                                    BlogIcon()
                                }
                            }
                        },
                        label = {
                            val resource = when (route) {
                                is HomeRoute    -> {
                                    Res.string.label_home
                                }

                                is ProfileRoute -> {
                                    Res.string.label_profile
                                }

                                is BlogRoute    -> {
                                    Res.string.label_blog
                                }

                                else            -> {
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
                        selected = selectedRoute == route,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                performRootNavigation(route)
                            }
                        },
                        icon = {
                            when (route) {
                                is HomeRoute    -> {
                                    HomeIcon()
                                }

                                is ProfileRoute -> {
                                    ProfileIcon()
                                }

                                is BlogRoute    -> {
                                    BlogIcon()
                                }
                            }
                        },
                        label = {
                            val resource = when (route) {
                                is HomeRoute    -> {
                                    Res.string.label_home
                                }

                                is ProfileRoute -> {
                                    Res.string.label_profile
                                }

                                is BlogRoute    -> {
                                    Res.string.label_blog
                                }

                                else            -> {
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
                            Text(text = stringResource(Res.string.label_website_name))
                        }
                    )
                }
            ) { contentPadding ->

                val contentModifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)

                NavHost(
                    navController = navController,
                    modifier = contentModifier,
                    startDestination = BASE_HOME_ROUTE,
                ) {
                    home()
                    profile()
                    blog()
                }

            }
        }

        LaunchedEffect(navController) {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.route) {
                    BASE_HOME_ROUTE    -> {
                        selectedRoute = HomeRoute
                    }

                    BASE_PROFILE_ROUTE -> {
                        selectedRoute = ProfileRoute
                    }

                    BASE_BLOG_ROUTE    -> {
                        selectedRoute = BlogRoute
                    }
                }
            }
        }
    }

}
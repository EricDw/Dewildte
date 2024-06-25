@file:OptIn(ExperimentalMaterial3Api::class)

package presentation

import BlogIcon
import EditIcon
import HomeIcon
import ProfileIcon
import SearchIcon
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import design.space.VerticalSpacer100
import dewildte.composeapp.generated.resources.*
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
import presentation.editor.MARKDOWN_EDITOR_NAVIGATION_GRAPH_ROUTE
import presentation.editor.MarkdownEditorRoute
import presentation.editor.markdownEditorNavigationGraph
import presentation.home.HOME_NAVIGATION_GRAPH_ROUTE
import presentation.home.HomeRoute
import presentation.home.homeNavigationGraph
import presentation.navigation.Route
import presentation.profile.PROFILE_NAVIGATION_GRAPH_ROUTE
import presentation.profile.ProfileRoute
import presentation.profile.profileNavigationGraph

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
@Preview
fun WebApp() {
    val windowSizeClass = calculateWindowSizeClass()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val navController = rememberNavController()

    val showNavigationRail = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact

    val rootRoutes = listOf(
        HomeRoute,
        ProfileRoute,
        MarkdownEditorRoute,
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

                is MarkdownEditorRoute -> {
                    navController.navigate(
                        route = MARKDOWN_EDITOR_NAVIGATION_GRAPH_ROUTE
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

        AnimatedVisibility(
            visible = showNavigationRail,
            enter = slideInHorizontally(initialOffsetX = { fullWidth: Int ->
                -fullWidth
            }),
            exit = slideOutHorizontally(targetOffsetX = { fullWidth: Int ->
                -fullWidth
            }),
        ) {
            NavigationRail(
                header = {
                    //                VerticalSpacer100()
                    //
                    //                FloatingActionButton(
                    //                    elevation = FloatingActionButtonDefaults.loweredElevation(),
                    //                    onClick = {
                    //                        // TODO: Implement Search
                    //                    },
                    //                ) {
                    //                    SearchIcon()
                    //                }
                },
            ) {

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
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

                                    is MarkdownEditorRoute -> {
                                        EditIcon()
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

                                    is MarkdownEditorRoute -> {
                                        Res.string.label_markdown_editor
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
            }
        }

        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = !showNavigationRail,
                    enter = slideInVertically(initialOffsetY = { fullHeight: Int ->
                        fullHeight
                    }),
                    exit = slideOutVertically(targetOffsetY = { fullHeight: Int ->
                        fullHeight
                    }),
                ) {
                    NavigationBar {
                        rootRoutes.forEach { route ->

                            NavigationBarItem(
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

                                        is MarkdownEditorRoute -> {
                                            EditIcon()
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

                                        is MarkdownEditorRoute -> {
                                            Res.string.label_markdown_editor
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
                }
            }
        ) { contentPadding ->

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
                    markdownEditorNavigationGraph(
                        windowSizeClass = windowSizeClass
                    )
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

                MARKDOWN_EDITOR_NAVIGATION_GRAPH_ROUTE -> {
                    selectedRoute = MarkdownEditorRoute
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
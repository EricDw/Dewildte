package presentation.editor

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import presentation.navigation.Route

const val MARKDOWN_EDITOR_NAVIGATION_GRAPH_ROUTE: String = "markdown-editor-graph"
private const val MARKDOWN_EDITOR_ROUTE = "editor"
private const val START_DESTINATION = MARKDOWN_EDITOR_ROUTE

object MarkdownEditorRoute : Route

fun NavGraphBuilder.markdownEditorNavigationGraph(
    onLaunch: () -> Unit = {},
) {
    navigation(
        startDestination = START_DESTINATION,
        route = MARKDOWN_EDITOR_NAVIGATION_GRAPH_ROUTE
    ) {
        composable(
            route = MARKDOWN_EDITOR_ROUTE
        ) {
            MarkdownEditorScreenController()

            LaunchedEffect(onLaunch) {
                onLaunch()
            }
        }

        onLaunch()
    }
}
package presentation.editor

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import dewildte.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MarkdownEditorScreenController(
    windowSizeClass: WindowSizeClass
) {

    var state by remember {
        mutableStateOf(
            value = MarkdownEditorScreenState(
                markdown = "**Write** here, _preview_ there ->",
            )
        )
    }

    MarkdownEditorScreen(
        state = state,
        onShowSampleClick = {
            state = state.copy(
                showSampleMarkdown = true,
            )
        },
        onHideSampleClick = {
            state = state.copy(
                showSampleMarkdown = false,
            )
        },
        onShowPreviewPanelClick = {
            println("Showing: Preview Panel")
            state = state.copy(
                showPreviewPanel = true
            )
        },
        onHidePreviewPanelClick = {
            println("Hiding: Preview Panel")
            state = state.copy(
                showPreviewPanel = false
            )
        },
        onMarkdownChange = { newMarkdown ->
            state = state.copy(
                markdown = newMarkdown,
            )
        },
    )

    LaunchedEffect(Unit) {
        try {
            val sample = Res
                .readBytes("files/sample.md")
                .decodeToString()

            state = state.copy(
                sampleMarkdown = sample
            )

        } catch (exception: Throwable) {
            println(exception)
        }
    }

    LaunchedEffect(windowSizeClass) {
        val showTwoPanel = when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Medium -> {
                true
            }

            WindowWidthSizeClass.Compact -> {
                false
            }

            else -> {
                // Expanded
                true
            }
        }

        state = state.copy(
            showTwoPanel = showTwoPanel
        )

    }
}
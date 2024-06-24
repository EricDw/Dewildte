package presentation.editor

import androidx.compose.runtime.*
import dewildte.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MarkdownEditorScreenController() {

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
}
package presentation.editor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun MarkdownEditorScreenController() {
    val state by remember {
        mutableStateOf(
            value = MarkdownEditorScreenState(
                markdown = "**Write** here, _preview_ there ->"
            )
        )
    }

    MarkdownEditorScreen(
        state = state
    )
}
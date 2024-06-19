package presentation.editor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.forEachTextValue
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import design.space.spacing
import design.text.Markdown

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MarkdownEditorScreen(
    modifier: Modifier = Modifier,
    state: MarkdownEditorScreenState = MarkdownEditorScreenState(),
    onMarkdownChange: (newMarkdown: String) -> Unit = {},
) {
    val (markdown) = state

    val currentMarkdownState by remember {
        val value = TextFieldState(
            initialText = markdown,
        )
        mutableStateOf(
            value = value
        )
    }

    val containerModifier = Modifier
        .fillMaxSize()
        .padding(MaterialTheme.spacing.spacing200.dp)

    val textFieldModifier = Modifier
        .fillMaxSize()
        .padding(MaterialTheme.spacing.spacing200.dp)

    Row(
        modifier = containerModifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spacing100.dp)
    ) {

        val cardModifier = Modifier
            .fillMaxSize()
            .weight(0.5F)

        Card(
            modifier = cardModifier,
        ) {
            BasicTextField2(
                modifier = textFieldModifier,
                state = currentMarkdownState,
            )
        }

        Card(
            modifier = cardModifier,
        ) {
            currentMarkdownState.text.toString()
                .takeIf { it.isNotBlank() }
                ?.let {
                    Markdown(
                        value = it,
                        modifier = textFieldModifier
                    )
                }
        }
    }

    LaunchedEffect(currentMarkdownState) {
        currentMarkdownState.forEachTextValue { newValue ->
            onMarkdownChange(newValue.toString())
        }
    }
}
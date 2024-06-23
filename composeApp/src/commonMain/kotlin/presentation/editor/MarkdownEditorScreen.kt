package presentation.editor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text2.input.forEachTextValue
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import design.space.spacing
import design.text.Markdown

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MarkdownEditorScreen(
    modifier: Modifier = Modifier,
    state: MarkdownEditorScreenState = MarkdownEditorScreenState(),
    onToggleSampleClick: () -> Unit = {},
    onMarkdownChange: (newMarkdown: String) -> Unit = {},
) {
    val (markdown, showSampleMarkdown, sampleMarkdown) = state

    var currentMarkdownValue by remember {
        mutableStateOf(
            value = markdown
        )
    }

    val containerModifier = Modifier
        .fillMaxSize()

    val textFieldModifier = Modifier
        .fillMaxSize()
        .padding(MaterialTheme.spacing.spacing200.dp)

    Column(
        modifier = containerModifier
    ) {

        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Text(text = "Markdown Editor")
            },
            actions = {
                val label = if (showSampleMarkdown) {
                    "Hide Sample"
                } else {
                    "Show Sample"
                }
                Text(
                    text = label,
                    modifier = Modifier.clickable {
                        onToggleSampleClick()
                    }
                )
            },
        )

        Row(
            modifier = Modifier.padding(MaterialTheme.spacing.spacing200.dp),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spacing100.dp)
        ) {

            val cardModifier = Modifier
                .fillMaxSize()
                .weight(0.5F)

            Card(
                modifier = cardModifier,
            ) {
                val value = if (showSampleMarkdown) {
                    sampleMarkdown
                } else {
                    currentMarkdownValue
                }
                BasicTextField(
                    modifier = textFieldModifier,
                    value = value,
                    onValueChange = { newValue ->
                        currentMarkdownValue = newValue
                    },
                    readOnly = showSampleMarkdown
                )
            }

            val previewMarkdown = if (showSampleMarkdown) {
                sampleMarkdown
            } else {
                markdown
            }

            PreviewPanel(
                markdown = previewMarkdown,
                modifier = cardModifier
            )
        }
    }

    LaunchedEffect(currentMarkdownValue) {
        onMarkdownChange(currentMarkdownValue)
    }
}

@Composable
fun PreviewPanel(
    markdown: String = "",
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
    ) {

        val markdownModifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.spacing200.dp)

        Markdown(
            value = markdown,
            modifier = markdownModifier
        )

    }

}
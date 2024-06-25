package presentation.editor

import EditIcon
import EditOffIcon
import PreviewIcon
import PreviewOffIcon
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.dp
import design.space.spacing
import design.text.Markdown

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MarkdownEditorScreen(
    modifier: Modifier = Modifier,
    state: MarkdownEditorScreenState = MarkdownEditorScreenState(),
    onShowSampleClick: () -> Unit = {},
    onHideSampleClick: () -> Unit = {},
    onShowPreviewPanelClick: () -> Unit = {},
    onHidePreviewPanelClick: () -> Unit = {},
    onMarkdownChange: (newMarkdown: String) -> Unit = {},
) {
    val (markdown,
        sampleMarkdown,
        showSampleMarkdown,
        showTwoPanel,
        showPreviewPanel) = state

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

    val panelWeight = if (showTwoPanel) {
        0.5F
    } else {
        1F
    }

    Column(
        modifier = containerModifier
    ) {

        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Text(text = "Markdown Editor")
            },
            actions = {

                if (showTwoPanel) {
                    IconToggleButton(
                        checked = showSampleMarkdown,
                        onCheckedChange = { checked ->
                            if (checked) {
                                onShowSampleClick()
                            } else {
                                onHideSampleClick()
                            }
                        },
                    ) {
                        if (showSampleMarkdown) {
                            EditIcon()
                        } else {
                            EditOffIcon()
                        }
                    }
                }

                IconToggleButton(
                    checked = showPreviewPanel,
                    onCheckedChange = { checked ->
                        if (checked) {
                            onShowPreviewPanelClick()
                        } else {
                            onHidePreviewPanelClick()
                        }
                    },
                ) {
                    if (showPreviewPanel) {
                        PreviewOffIcon()
                    } else {
                        PreviewIcon()
                    }
                }
            },
        )

        Row(
            modifier = Modifier.padding(MaterialTheme.spacing.spacing200.dp),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spacing100.dp),
        ) {

            val cardModifier = Modifier
                .fillMaxSize()
                .weight(panelWeight)

            AnimatedVisibility(
                modifier = cardModifier,
                visible = showTwoPanel || !showPreviewPanel,
                enter = slideInHorizontally(
                    initialOffsetX = { fullWidth: Int ->
                        -fullWidth
                    }
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { fullWidth: Int ->
                        -fullWidth
                    }
                )
            ) {
                Card(
                    modifier = Modifier.fillMaxSize(),
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
            }

            val previewMarkdown = if (showSampleMarkdown) {
                sampleMarkdown
            } else {
                markdown
            }

            AnimatedVisibility(
                modifier = cardModifier,
                visible = showPreviewPanel,
                enter = slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }),
                exit = slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }),
            ) {
                PreviewPanel(
                    markdown = previewMarkdown,
                    modifier = Modifier.fillMaxSize()
                )
            }
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

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Markdown(
                    value = markdown,
                    modifier = markdownModifier
                )
            }
        }

    }

}
package presentation.editor

data class MarkdownEditorScreenState(
    val markdown: String = "",
    val sampleMarkdown: String = "",
    val showSampleMarkdown: Boolean = true,
    val showTwoPanel: Boolean = true,
    val showPreviewPanel: Boolean = true
)

package presentation.editor

data class MarkdownEditorScreenState(
    val markdown: String = "",
    val showSampleMarkdown: Boolean = true,
    val sampleMarkdown: String = ""
)

package parsing.markdown

data class MarkdownAST(
	val nodes: List<MarkdownNode> = emptyList(),
)
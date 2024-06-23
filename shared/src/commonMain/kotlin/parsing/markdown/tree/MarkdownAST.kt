package parsing.markdown.tree

import parsing.markdown.MarkdownNode

data class MarkdownAST(
	val nodes: List<MarkdownNode> = emptyList(),
)
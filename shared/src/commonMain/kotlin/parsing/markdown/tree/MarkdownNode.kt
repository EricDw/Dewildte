package parsing.markdown

sealed interface MarkdownNode  {
	data class Heading(
		val value: String,
	): MarkdownNode
}
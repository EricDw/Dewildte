package parsing.markdown

sealed class MarkdownSpan {
	
	data class BoldSpan(
		val start: Int,
		val end: Int,
	): MarkdownSpan()
	
}
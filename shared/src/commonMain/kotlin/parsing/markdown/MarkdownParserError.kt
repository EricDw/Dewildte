package parsing.markdown

data class MarkdownParserError(
	override val message: String,
	override val cause: Throwable? = null,
): Throwable()
package parsing.text

data class TextParserError(
	override val message: String,
	override val cause: Throwable? = null
): Throwable()
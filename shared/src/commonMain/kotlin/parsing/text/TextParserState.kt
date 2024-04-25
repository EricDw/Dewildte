package parsing.text

data class TextParserState(
	val text: String,
	val position: Int = 0,
)
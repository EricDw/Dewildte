package parsing.text

data class TextParserState(
	val characters: Iterable<Char>,
	val position: Int = 0,
)
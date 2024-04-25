package parsing.text

import parsing.Parser

class CharParser(
	private val charToMatch: Char,
	private val ignoreCase: Boolean = false,
) : TextParser<Char> {

	override fun invoke(
		input: TextParserState
	): TextParserResult<Char> {

		val parser = satisfies { char ->
			char.equals(other = charToMatch, ignoreCase = ignoreCase)
		}

		return parser(input)
	}

}

fun Char.toTextParser(ignoreCase: Boolean = false): TextParser<Char> {
	return CharParser(charToMatch = this, ignoreCase = ignoreCase)
}

/**
 * Ensures the [CharParser] functions correctly.
 */
private fun main() {

	val expected = Parser.Result.Match<TextParserState, Char, Throwable>(
		nextInput = TextParserState(text = "A", position = 1),
		matchedItem = 'A',
	)

	val parser = 'A'.toTextParser()

	val actual = parser(
		input = TextParserState("A"),
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
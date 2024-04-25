package parsing.text

import parsing.Parser

class CharParser(
	private val charToMatch: Char,
	private val ignoreCase: Boolean = false,
) : TextParser<Char, Throwable> {
	override fun invoke(
		input: TextParserState
	): TextParserResult<Char, Throwable> {

		val (chars, position) = input

		val char: Char = chars.elementAt(position)

		val isMatch: Boolean = charToMatch.equals(
			other = char,
			ignoreCase = ignoreCase
		)

		return when (isMatch) {
			true -> {
				Parser.Result.Match(
					nextInput = input.copy(position = position.inc()),
					matchedItem = char,
				)
			}

			false -> {
				Parser.Result.Failure(
					originalInput = input,
					error = IllegalArgumentException("Expected: $char, Found: $charToMatch"),
				)
			}
		}

	}

}

/**
 * Ensures the [CharParser] functions correctly.
 */
private fun main() {

	val expected = Parser.Result.Match<TextParserState, Char, Throwable>(
		nextInput = TextParserState(characters = listOf('A'), position = 1),
		matchedItem = 'A',
	)

	val parser = CharParser(charToMatch = 'A')

	val actual = parser(
		input = TextParserState(listOf('A')),
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
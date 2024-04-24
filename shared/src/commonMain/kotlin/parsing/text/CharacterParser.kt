package parsing.text

import parsing.Parser

class CharacterParser(
	private val charToMatch: Char,
	private val ignoreCase: Boolean = false,
) : TextParser<Char, Throwable> {
	override fun invoke(
		input: TextParserState
	): TextParserResult<Char, Throwable> {
		
		val (characters, position) = input
		
		val character: Char = characters.elementAt(position)

		val isMatch: Boolean = charToMatch.equals(
			other = character,
			ignoreCase = ignoreCase
		)

		return when (isMatch) {
			true -> {
				Parser.Result.Match(
					nextInput = input.copy(position = position.inc()),
					matchedItem = character,
				)
			}

			false -> {
				Parser.Result.Failure(
					originalInput = input,
					error = IllegalArgumentException("Expected: $character, Found: $charToMatch"),
				)
			}
		}

	}

}

/**
 * Ensures the [CharacterParser] functions correctly.
 */
private fun main() {

	val expected = Parser.Result.Match<TextParserState, Char, Throwable>(
		nextInput = TextParserState(characters = listOf('A'), position = 1),
		matchedItem = 'A',
	)

	val parser = CharacterParser(charToMatch = 'A')

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
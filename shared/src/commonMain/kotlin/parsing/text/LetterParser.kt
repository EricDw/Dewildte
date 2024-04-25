package parsing.text

import parsing.Parser

class LetterParser : TextParser<Char> {

	override fun invoke(
		input: TextParserState
	): TextParserResult<Char> {

		val parser = satisfies { char ->
			char.isLetter()
		}

		return parser(input)
	}

}

/**
 * Ensures the [LetterParser] functions correctly.
 */
private fun main() {

	val expected = Parser.Result.Match<TextParserState, Char, Throwable>(
		nextInput = TextParserState(text = "A", position = 1),
		matchedItem = 'A',
	)

	val parser = LetterParser()

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
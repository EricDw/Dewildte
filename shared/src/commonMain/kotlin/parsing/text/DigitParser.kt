package parsing.text

import parsing.Parser

class DigitParser : TextParser<Char> {

	override fun invoke(
		input: TextParserState
	): TextParserResult<Char> {

		val parser = satisfies { char ->
			char.isDigit()
		}

		return parser(input)
	}

}

/**
 * Ensures the [DigitParser] functions correctly.
 */
private fun main() {

	val expected = Parser.Result.Match<TextParserState, Char, Throwable>(
		nextInput = TextParserState(text = "1", position = 1),
		matchedItem = '1',
	)

	val parser = DigitParser()

	val actual = parser(
		input = TextParserState("1"),
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
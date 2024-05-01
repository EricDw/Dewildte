package parsing.text

import parsing.Parser
import parsing.map
import parsing.oneOrMore

class WordParser : TextParser<String> {
	override fun invoke(
		input: TextParserState
	): TextParserResult<String> {

		val letters = LetterParser()

		val parser =
			oneOrMore(letters) map { chars ->
				chars.joinToString("")
			}

		return parser(input)
	}

}

/**
 * Ensures the [WordParser] functions correctly.
 */
private fun main() {

	val expected = Parser.Result.Match<TextParserState, String, Throwable>(
		nextInput = TextParserState(text = "Hello, World", position = 5),
		matchedItem = "Hello",
	)

	val parser = WordParser()

	val actual = parser(
		input = TextParserState("Hello, World"),
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
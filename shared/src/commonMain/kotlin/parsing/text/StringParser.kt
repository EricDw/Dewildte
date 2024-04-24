package parsing.text

import parsing.Parser
import parsing.join

class StringParser(
	private val stringToMatch: String,
	private val ignoreCase: Boolean = false,
) : TextParser<String, Throwable> {
	override fun invoke(
		input: TextParserState
	): Parser.Result<TextParserState, String, Throwable> {

		fun Char.toParser(): CharacterParser {
			return CharacterParser(charToMatch = this, ignoreCase = ignoreCase)
		}

		return stringToMatch
			.map(Char::toParser)
			.join { chars ->
				chars.joinToString("")
			}
			.invoke(input)
	}

}

/**
* Ensures the [StringParser] functions correctly.
*/
private fun main() {

	val expected = Parser.Result.Match<TextParserState, String, Throwable>(
		nextInput = TextParserState("Hello, World".toList(), position = 5),
		matchedItem = "Hello",
	)

	val parser = StringParser(stringToMatch = "Hello")

	val actual = parser(
		input = TextParserState("Hello, World".toList()),
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
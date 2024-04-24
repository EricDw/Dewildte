package parsing.text

import parsing.Parser

class CharParser(
	private val charToMatch: Char,
	private val ignoreCase: Boolean = false,
) : Parser<String, Char, Throwable> {
	override fun invoke(
		input: String
	): Parser.Result<String, Char, Throwable> {
		val character: Char = input.first()

		val isMatch: Boolean = charToMatch.equals(
			other = character,
			ignoreCase = ignoreCase
		)

		return when (isMatch) {
			true -> {
				Parser.Result.Match(
					nextInput = input.drop(1),
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
 * Ensures the [CharParser] functions correctly.
 */
private fun main() {

	val expected: Parser.Result.Match<String, Char, Throwable> = Parser.Result.Match(
		nextInput = "",
		matchedItem = 'A',
	)

	val parser = CharParser(charToMatch = 'A')

	val actual: Parser.Result<String, Char, Throwable> = parser(
		input = "A",
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
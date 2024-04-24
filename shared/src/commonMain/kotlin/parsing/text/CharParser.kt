package parsing

class CharParser(
	private val charToMatch: Char,
	private val ignoreCase: Boolean = false,
) : Parser<String, Char> {
	override fun invoke(
		input: String
	): Parser.Result<String, Char> {
		val character: Char = input.first()

		val isMatch: Boolean = charToMatch.equals(
			other = character,
			ignoreCase = ignoreCase
		)

		return when (isMatch) {
			true -> {
				Parser.Result.Match(
					nextInput = input,
					matchedItem = character,
				)
			}

			false -> {
				Parser.Result.Failure(
					originalInput = input,
					error = IllegalArgumentException("Expected: $character, at position: ${input.first()}, Found: $charToMatch"),
				)
			}
		}

	}

}

/**
 * Ensures the [CharParser] functions correctly.
 */
private fun main() {

	val expected: Parser.Result.Match<String, Char> = Parser.Result.Match(
		nextInput = "A",
		matchedItem = 'A',
	)

	val parser: CharParser = CharParser(charToMatch = 'A')

	val actual: Parser.Result<String, Char> = parser(
		input = "A",
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
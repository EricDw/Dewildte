package parsing

class CharParser<USER_STATE>(
	private val charToMatch: Char,
	private val ignoreCase: Boolean = false,
) : Parser<USER_STATE, Char, Char> {
	override fun invoke(
		input: Parser.Input<USER_STATE, Char>
	): Parser.Result<USER_STATE, Char, Char> {
		val character: Char = input.items.elementAt(input.position)

		val isMatch: Boolean = charToMatch.equals(
			other = character,
			ignoreCase = ignoreCase
		)

		return when (isMatch) {
			true -> {
				val output: Parser.Input<USER_STATE, Char> = input.copy(
					position = input.position.inc()
				)

				Parser.Result.Match(
					nextInput = output,
					matchedItem = character,
				)
			}

			false -> {
				Parser.Result.Failure(
					originalInput = input,
					error = IllegalArgumentException("Expected: $character, at position: ${input.position}, Fo und: $charToMatch"),
				)
			}
		}

	}

}

/**
 * Ensures the [CharParser] functions correctly.
*/
private fun main() {

	val expected: Parser.Result.Match<Unit, Char, Char> = Parser.Result.Match(
		nextInput = Parser.Input(
			items = listOf('A'),
			userState = Unit,
			position = 1,
		),
		matchedItem = 'A',
	)

	val parser: CharParser<Unit> = CharParser(charToMatch = 'A')

	val actual: Parser.Result<Unit, Char, Char> = runParser(
		userState = Unit,
		itemsToParse = listOf('A'),
		parser,
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
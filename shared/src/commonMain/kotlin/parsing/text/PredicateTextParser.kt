package parsing.text

import parsing.Parser

class PredicateTextParser(
	private val predicate: (obj: Char) -> Boolean,
) : TextParser<Char> {
	override fun invoke(
		input: TextParserState
	): TextParserResult<Char> {

		val (text, position) = input

		if (text.isEmpty()) {
			return Parser.Result.Failure(
				input, TextParserError("End Of File")
			)
		}

		val char = text[position]

		return when (predicate(char)) {

			true -> {
				Parser.Result.Match(
					nextInput = input.copy(position = position.inc()),
					matchedItem = char,
				)
			}

			false -> {
				val message = buildString {
					val window = text.windowed(size = 20)
					appendLine("Window: $window")
					appendLine("Position: $position")
					appendLine("Expected: $char")
					appendLine("Found: $char")
				}
				Parser.Result.Failure(
					originalInput = input,
					error = TextParserError(
						message = message
					),
				)
			}
		}

	}

}

fun satisfies(
	predicate: (obj: Char) -> Boolean,
): TextParser<Char> {
	return PredicateTextParser(predicate)
}

/**
 * Ensures the [PredicateTextParser] functions correctly.
 */
private fun main() {

	val expected: TextParserResult<Char> = Parser.Result.Match(
		nextInput = TextParserState("A", position = 1),
		matchedItem = 'A',
	)

	val parser = satisfies { char ->
		char.isLetter()
	}

	val actual = parser(
		input = TextParserState("A")
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
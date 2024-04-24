package parsing

class PredicateParser<INPUT : Any>(
	private val predicate: (obj: INPUT) -> Boolean,
) : AbstractParser<INPUT, INPUT>() {
	override fun parse(
		input: INPUT
	): Parser.Result<INPUT, INPUT> {
		return when (predicate(input)) {

			true -> {
				match(
					originalInput = input,
					output = input,
				)
			}

			false -> {
				fail(
					originalInput = input,
					error = IllegalArgumentException("Item: $input, Failed to match the given predicate"),
				)
			}
		}

	}

}

/**
 * Ensures the [PredicateParser] functions correctly.
 */
private fun main() {

	val expected: Parser.Result.Match<Int, Int> = Parser.Result.Match(
		nextInput = 0,
		matchedItem = 0,
	)

	val parser: PredicateParser<Int> = PredicateParser { item: Int ->
		item == 0
	}

	val actual: Parser.Result<Int, Int> = parser(
		input = 0
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
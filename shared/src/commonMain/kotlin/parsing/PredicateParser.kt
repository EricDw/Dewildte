package parsing

class PredicateParser<INPUT : Any, ERROR: Throwable>(
	private val predicate: (obj: INPUT) -> Boolean,
) : Parser<INPUT, INPUT, ERROR> {
	override fun invoke(
		input: INPUT
	): Parser.Result<INPUT, INPUT, ERROR> {
		return when (predicate(input)) {

			true -> {
				Parser.Result.Match(
					nextInput = input,
					matchedItem = input,
				)
			}

			false -> {
				Parser.Result.Failure(
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

	val expected: Parser.Result.Match<Int, Int, Throwable> = Parser.Result.Match(
		nextInput = 0,
		matchedItem = 0,
	)

	val parser: PredicateParser<Int, Throwable> = PredicateParser { item: Int ->
		item == 0
	}

	val actual: Parser.Result<Int, Int, Throwable> = parser(
		input = 0
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
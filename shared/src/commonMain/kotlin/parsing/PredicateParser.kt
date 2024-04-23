package parsing

class PredicateParser<USER_STATE, INPUT : Any>(
	private val predicate: (obj: INPUT) -> Boolean,
) : Parser<USER_STATE, INPUT, INPUT> {
	override fun invoke(
		input: Parser.Input<USER_STATE, INPUT>
	): Parser.Result<USER_STATE, INPUT, INPUT> {
		val item: INPUT = input.items.elementAt(input.position)

		return when (predicate(item)) {

			true -> {
				val output = input.copy(
					position = input.position.inc()
				)
				Parser.Result.Match(
					nextInput = output,
					matchedItem = item,
				)
			}

			false -> {
				Parser.Result.Failure(
					originalInput = input,
					error = IllegalArgumentException("Item: $item, Failed to match the given predicate"),
				)
			}
		}

	}

}

/**
 * Ensures the [PredicateParser] functions correctly.
 */
private fun main() {

	val expected: Parser.Result.Match<Unit, Int, Int> = Parser.Result.Match(
		nextInput = Parser.Input(
			items = listOf(0),
			userState = Unit,
			position = 1,
		),
		matchedItem = 0,
	)

	val parser: PredicateParser<Unit, Int> = PredicateParser { item: Int ->
		item == 0
	}

	val actual: Parser.Result<Unit, Int, Int> = runParser(
		userState = Unit,
		itemsToParse = listOf(0),
		parser
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
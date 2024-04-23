package parsing

class AndParser<USER_STATE, INPUT : Any, OUTPUT_A : Any, OUTPUT_B : Any>(
	private val firstParser: Parser<USER_STATE, INPUT, OUTPUT_A>,
	private val secondParser: Parser<USER_STATE, INPUT, OUTPUT_B>,
) : Parser<USER_STATE, INPUT, Pair<OUTPUT_A, OUTPUT_B>> {

	override fun invoke(
		input: Parser.Input<USER_STATE, INPUT>
	): Parser.Result<USER_STATE, INPUT, Pair<OUTPUT_A, OUTPUT_B>> {
		return when (
			val firstResult = firstParser(input)
		) {
			is Parser.Result.Failure -> {
				Parser.Result.Failure(
					originalInput = input,
					error = firstResult.error,
				)
			}

			is Parser.Result.Match -> {
				when (
					val secondResult = secondParser(firstResult.nextInput)
				) {
					is Parser.Result.Failure -> {
						Parser.Result.Failure(
							originalInput = secondResult.originalInput,
							error = secondResult.error,
						)
					}

					is Parser.Result.Match -> {
						Parser.Result.Match(
							nextInput = secondResult.nextInput,
							matchedItem = firstResult.matchedItem to secondResult.matchedItem,
						)
					}
				}
			}
		}
	}

}

infix fun <USER_STATE, INPUT : Any, OUTPUT_A : Any, OUTPUT_B : Any> Parser<USER_STATE, INPUT, OUTPUT_A>.and(
	other: Parser<USER_STATE, INPUT, OUTPUT_B>,
): Parser<USER_STATE, INPUT, Pair<OUTPUT_A, OUTPUT_B>> {
	return AndParser(
		firstParser = this,
		secondParser = other
	)
}

/**
 * Ensures the [AndParser] functions correctly.
 */
private fun main() {

	val expected: Parser.Result.Match<Unit, Any, Pair<Int, String>> = Parser.Result.Match(
		nextInput = Parser.Input(
			items = listOf(1, "Two"),
			userState = Unit,
			position = 2,
		),
		matchedItem = 1 to "Two",
	)

	val firstParser = Parser { input: Parser.Input<Unit, Any> ->
		val item = input.items.elementAt(input.position)

		if (item is Int) {
			Parser.Result.Match(
				nextInput = input.copy(position = input.position.inc()),
				matchedItem = item,
			)
		} else {
			Parser.Result.Failure(
				originalInput = input,
				error = Throwable("Failed to find an integer")
			)
		}
	}
	
	val secondParser = Parser { input: Parser.Input<Unit, Any> ->
		val item = input.items.elementAt(input.position)

		if (item is String) {
			Parser.Result.Match(
				nextInput = input.copy(position = input.position.inc()),
				matchedItem = item,
			)
		} else {
			Parser.Result.Failure(
				originalInput = input,
				error = Throwable("Failed to find a string")
			)
		}
	}

	val actual: Parser.Result<Unit, Any, Pair<Int, String>> = runParser(
		userState = Unit,
		itemsToParse = listOf(1, "Two"),
		firstParser and secondParser,
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
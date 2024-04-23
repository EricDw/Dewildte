package parsing

class OrParser<USER_STATE, INPUT : Any, OUTPUT : Any>(
	private val firstParser: Parser<USER_STATE, INPUT, OUTPUT>,
	private val secondParser: Parser<USER_STATE, INPUT, OUTPUT>,
) : Parser<USER_STATE, INPUT, OUTPUT> {

	override fun invoke(
		input: Parser.Input<USER_STATE, INPUT>
	): Parser.Result<USER_STATE, INPUT, OUTPUT> {
		return when (
			val firstResult = firstParser(input)
		) {
			is Parser.Result.Failure -> {
				secondParser(input)
			}

			is Parser.Result.Match -> {
				firstResult
			}
		}
	}

}

infix fun <USER_STATE, INPUT : Any, OUTPUT : Any> Parser<USER_STATE, INPUT, OUTPUT>.or(
	other: Parser<USER_STATE, INPUT, OUTPUT>,
): Parser<USER_STATE, INPUT, OUTPUT> {
	return OrParser(
		firstParser = this,
		secondParser = other
	)
}

/**
 * Ensures the [OrParser] functions correctly.
 */
private fun main() {

	val expectedA: Parser.Result.Match<Unit, Any, String> = Parser.Result.Match(
		nextInput = Parser.Input(
			items = listOf("One", "Two"),
			userState = Unit,
			position = 1,
		),
		matchedItem = "One",
	)
	
	val expectedB: Parser.Result.Match<Unit, Any, String> = Parser.Result.Match(
		nextInput = Parser.Input(
			items = listOf("Two", "One"),
			userState = Unit,
			position = 1,
		),
		matchedItem = "Two",
	)
	
	val firstParser = Parser { input: Parser.Input<Unit, String> ->
		val item = input.items.elementAt(input.position)

		if (item == "One") {
			Parser.Result.Match(
				nextInput = input.copy(position = input.position.inc()),
				matchedItem = item,
			)
		} else {
			Parser.Result.Failure(
				originalInput = input,
				error = Throwable("Expected: \"One\", Found: $item")
			)
		}
	}

	val secondParser = Parser { input: Parser.Input<Unit, String> ->
		val item = input.items.elementAt(input.position)

		if (item == "Two") {
			Parser.Result.Match(
				nextInput = input.copy(position = input.position.inc()),
				matchedItem = item,
			)
		} else {
			Parser.Result.Failure(
				originalInput = input,
				error = Throwable("Expected: \"Two\", Found: $item")
			)
		}
	}

	val actualA: Parser.Result<Unit, String, String> = runParser(
		userState = Unit,
		itemsToParse = listOf("One", "Two"),
		firstParser or secondParser,
	)
	
	val actualB: Parser.Result<Unit, String, String> = runParser(
		userState = Unit,
		itemsToParse = listOf("Two", "One"),
		firstParser or secondParser,
	)

	check(
		value = actualA == expectedA
	) {
		"\n\tExpected: $expectedA\n\tActual: $actualA"
	}

	check(
		value = actualB == expectedB
	) {
		"\n\tExpected: $expectedB\n\tActual: $actualB"
	}

	println("Success")
}
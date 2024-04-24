package parsing

class OrParser<INPUT : Any, OUTPUT : Any, ERROR: Throwable>(
	private val firstParser: Parser<INPUT, OUTPUT, ERROR>,
	private val secondParser: Parser<INPUT, OUTPUT, ERROR>,
) : Parser<INPUT, OUTPUT, ERROR> {

	override fun invoke(
		input: INPUT
	): Parser.Result<INPUT, OUTPUT, ERROR> {
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

infix fun <INPUT : Any, OUTPUT : Any, ERROR: Throwable> Parser<INPUT, OUTPUT, ERROR>.or(
	other: Parser<INPUT, OUTPUT, ERROR>,
): Parser<INPUT, OUTPUT, ERROR> {
	return OrParser(
		firstParser = this,
		secondParser = other
	)
}

/**
 * Ensures the [OrParser] functions correctly.
 */
private fun main() {

	data class AnyStream(
		val data: Iterable<Any>,
		val position: Int = 0,
	)

	val expectedA: Parser.Result.Match<AnyStream, String, Throwable> = Parser.Result.Match(
		nextInput = AnyStream(
			data = listOf("One", "Two"),
			position = 1,
		),
		matchedItem = "One",
	)

	val expectedB: Parser.Result.Match<AnyStream, String, Throwable> = Parser.Result.Match(
		nextInput = AnyStream(
			data = listOf("Two", "One"),
			position = 1,
		),
		matchedItem = "Two",
	)

	val firstParser = Parser { input: AnyStream ->
		val item = input.data.elementAt(input.position)

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

	val secondParser = Parser { input: AnyStream ->
		val item = input.data.elementAt(input.position)

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

	val parser = firstParser or secondParser

	val actualA: Parser.Result<AnyStream, Any, Throwable> = parser(
		input = AnyStream(listOf("One", "Two"))
	)

	val actualB: Parser.Result<AnyStream, Any, Throwable> = parser(
		input = AnyStream(listOf("Two", "One"))
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
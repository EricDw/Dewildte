package parsing

class AndParser<INPUT, OUTPUT_A, OUTPUT_B, ERROR>(
	private val firstParser: Parser<INPUT, OUTPUT_A, ERROR>,
	private val secondParser: Parser<INPUT, OUTPUT_B, ERROR>,
) : Parser<INPUT, Pair<OUTPUT_A, OUTPUT_B>, ERROR> {

	override fun invoke(
		input: INPUT
	): Parser.Result<INPUT, Pair<OUTPUT_A, OUTPUT_B>, ERROR> {

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

infix fun <INPUT, OUTPUT_A, OUTPUT_B, ERROR> Parser<INPUT, OUTPUT_A, ERROR>.and(
	other: Parser<INPUT, OUTPUT_B, ERROR>,
): Parser<INPUT, Pair<OUTPUT_A, OUTPUT_B>, ERROR> {
	return AndParser(
		firstParser = this,
		secondParser = other
	)
}

/**
 * Ensures the [AndParser] functions correctly.
 */
private fun main() {

	data class AnyStream(
		val data: Iterable<Any>,
		val position: Int = 0,
	)

	val expected: Parser.Result.Match<AnyStream, Pair<Int, String>, Throwable> = Parser.Result.Match(
		nextInput = AnyStream(
			data = listOf(1, "Two"),
			position = 2,
		),
		matchedItem = 1 to "Two",
	)

	val firstParser = Parser { input: AnyStream ->
		val item = input.data.elementAt(input.position)

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

	val secondParser = Parser { input: AnyStream ->
		val item = input.data.elementAt(input.position)

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

	val parser = firstParser and secondParser

	val actual: Parser.Result<AnyStream, Pair<Int, String>, Throwable> = parser(
		input = AnyStream(
			data = listOf(1, "Two"),
		),
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
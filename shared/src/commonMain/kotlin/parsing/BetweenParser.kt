package parsing

class BetweenParser<INPUT : Any, OUTPUT_A : Any, OUTPUT_B : Any, OUTPUT_C : Any, ERROR: Throwable>(
	private val leftParser: Parser<INPUT, OUTPUT_A, ERROR>,
	private val middleParser: Parser<INPUT, OUTPUT_B, ERROR>,
	private val rightParser: Parser<INPUT, OUTPUT_C, ERROR>,
) : Parser<INPUT, OUTPUT_B, ERROR> {

	override fun invoke(
		input: INPUT
	): Parser.Result<INPUT, OUTPUT_B, ERROR> {

		return (leftParser and_ middleParser _and rightParser).invoke(input)

	}

}

fun <INPUT : Any, OUTPUT_A : Any, OUTPUT_B : Any, OUTPUT_C : Any, ERROR: Throwable> between(
	leftParser: Parser<INPUT, OUTPUT_A, ERROR>,
	middleParser: Parser<INPUT, OUTPUT_B, ERROR>,
	rightParser: Parser<INPUT, OUTPUT_C, ERROR>,
): Parser<INPUT, OUTPUT_B, ERROR> {
	return BetweenParser(
		leftParser = leftParser,
		middleParser = middleParser,
		rightParser = rightParser
	)
}

/**
 * Ensures the [BetweenParser] functions correctly.
 */
private fun main() {

	data class AnyStream(
		val data: Iterable<Any>,
		val position: Int = 0,
	)

	val expected: Parser.Result.Match<AnyStream, String, Throwable> = Parser.Result.Match(
		nextInput = AnyStream(
			data = listOf(1, "Two", 1),
			position = 3,
		),
		matchedItem = "Two",
	)

	val oneParser = Parser { input: AnyStream ->
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

	val twoParser: Parser<AnyStream, String, Throwable> = Parser { input: AnyStream ->
		val item = input.data.elementAt(input.position)

		if (item is String && item == "Two") {
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
	
	val parser = between(
		leftParser = oneParser,
		middleParser = twoParser,
		rightParser = oneParser
	)

	val actual: Parser.Result<AnyStream, String, Throwable> = parser(
		input = AnyStream(
			data = listOf(1, "Two", 1),
		),
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
package parsing

class BetweenParser<INPUT : Any, OUTPUT_A : Any, OUTPUT_B : Any, OUTPUT_C : Any>(
	private val leftParser: Parser<INPUT, OUTPUT_A>,
	private val middleParser: Parser<INPUT, OUTPUT_B>,
	private val rightParser: Parser<INPUT, OUTPUT_C>,
) : AbstractParser<INPUT, OUTPUT_B>() {

	override fun parse(
		input: INPUT
	): Parser.Result<INPUT, OUTPUT_B> {

		return (leftParser and_ middleParser _and rightParser).invoke(input)

	}

}

fun <INPUT : Any, OUTPUT_A : Any, OUTPUT_B : Any, OUTPUT_C : Any> between(
	leftParser: Parser<INPUT, OUTPUT_A>,
	middleParser: Parser<INPUT, OUTPUT_B>,
	rightParser: Parser<INPUT, OUTPUT_C>,
): Parser<INPUT, OUTPUT_B> {
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

	val expected: Parser.Result.Match<AnyStream, String> = Parser.Result.Match(
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

	val twoParser: Parser<AnyStream, String> = Parser { input: AnyStream ->
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

	val actual: Parser.Result<AnyStream, String> = parser(
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
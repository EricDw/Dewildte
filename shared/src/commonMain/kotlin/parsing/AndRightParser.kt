package parsing

class AndRightParser<INPUT : Any, OUTPUT_A : Any, OUTPUT_B : Any>(
	private val firstParser: Parser<INPUT, OUTPUT_A>,
	private val secondParser: Parser<INPUT, OUTPUT_B>,
) : AbstractParser<INPUT, OUTPUT_B>() {

	override fun parse(
		input: INPUT
	): Parser.Result<INPUT, OUTPUT_B> {

		return when (
			val firstResult = firstParser(input)
		) {
			is Parser.Result.Failure -> {
				fail(
					originalInput = input,
					error = firstResult.error,
				)
			}

			is Parser.Result.Match -> {
				when (
					val secondResult = secondParser(firstResult.nextInput)
				) {
					is Parser.Result.Failure -> {
						fail(
							originalInput = secondResult.originalInput,
							error = secondResult.error,
						)
					}

					is Parser.Result.Match -> {
						secondResult
					}
				}
			}
		}
	}

}

@Suppress("FunctionName")
infix fun <INPUT : Any, OUTPUT_A : Any, OUTPUT_B : Any> Parser<INPUT, OUTPUT_A>.and_(
	other: Parser<INPUT, OUTPUT_B>,
): Parser<INPUT, OUTPUT_B> {
	return AndRightParser(
		firstParser = this,
		secondParser = other
	)
}

/**
 * Ensures the [AndRightParser] functions correctly.
 */
private fun main() {

	data class AnyStream(
		val data: Iterable<Any>,
		val position: Int = 0,
	)

	val expected: Parser.Result.Match<AnyStream, String> = Parser.Result.Match(
		nextInput = AnyStream(
			data = listOf(1, "Two"),
			position = 2,
		),
		matchedItem = "Two",
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

	val parser = firstParser and_ secondParser

	val actual: Parser.Result<AnyStream, String> = parser(
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
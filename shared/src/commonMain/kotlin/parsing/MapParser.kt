package parsing

class MapParser<INPUT : Any, OUTPUT_A : Any, OUTPUT_B : Any, ERROR: Throwable>(
	private val parserToMap: Parser<INPUT, OUTPUT_A, ERROR>,
	private val mapper: (mathedItem: OUTPUT_A) -> OUTPUT_B,
) : Parser<INPUT, OUTPUT_B, ERROR> {

	override fun invoke(
		input: INPUT
	): Parser.Result<INPUT, OUTPUT_B, ERROR> {
		return when (
			val result = parserToMap(input)
		) {
			is Parser.Result.Failure -> {
				Parser.Result.Failure(
					originalInput = input,
					error = result.error,
				)
			}

			is Parser.Result.Match -> {
				with(result) {
					Parser.Result.Match(
						nextInput = nextInput,
						matchedItem = mapper(matchedItem),
					)
				}
			}
		}
	}

}

infix fun <INPUT : Any, OUTPUT_A : Any, OUTPUT_B : Any, ERROR: Throwable> Parser<INPUT, OUTPUT_A, ERROR>.map(
	mapper: (itemToMap: OUTPUT_A) -> OUTPUT_B,
): Parser<INPUT, OUTPUT_B, ERROR> {
	return MapParser(
		parserToMap = this,
		mapper = mapper,
	)
}

/**
 * Ensures the [MapParser] functions correctly.
 */
private fun main() {

	data class IntStream(
		val data: Iterable<Int>,
		val position: Int = 0,
	)

	val expected: Parser.Result.Match<IntStream, String, Throwable> = Parser.Result.Match(
		nextInput = IntStream(
			data = listOf(1),
			position = 1,
		),
		matchedItem = "1",
	)

	val parserToMap: Parser<IntStream, Int, Throwable> = Parser { input: IntStream ->
		val item = input.data.elementAt(input.position)

		if (item == 1) {
			Parser.Result.Match(
				nextInput = input.copy(position = input.position.inc()),
				matchedItem = item,
			)
		} else {
			Parser.Result.Failure(
				originalInput = input,
				error = Throwable("Failed to find integer: ${1}")
			)
		}
	}

	val parser = parserToMap map Any::toString

	val actual: Parser.Result<IntStream, String, Throwable> = parser(
		input = IntStream(data = listOf(1)),
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
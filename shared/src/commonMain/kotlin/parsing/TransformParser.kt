package parsing

class TransformParser<INPUT_A, OUTPUT_A, OUTPUT_B, ERROR_A, ERROR_B>(
	private val parserToMap: Parser<INPUT_A, OUTPUT_A, ERROR_A>,
	private val mapper: (OUTPUT_A) -> OUTPUT_B,
	private val errorMapper: (errorToMap: ERROR_A) -> ERROR_B,
) : Parser<INPUT_A, OUTPUT_B, ERROR_B> {

	override fun invoke(
		input: INPUT_A
	): Parser.Result<INPUT_A, OUTPUT_B, ERROR_B> {
	
		val result =
			parserToMap(input)

		return when (result) {
			is Parser.Result.Failure -> {
				Parser.Result.Failure(
					originalInput = input,
					error = errorMapper(result.error),
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

fun <INPUT_A, OUTPUT_A, OUTPUT_B, ERROR_A, ERROR_B> transform(
	parserToMap: Parser<INPUT_A, OUTPUT_A, ERROR_A>,
	mapper: (itemToMap: OUTPUT_A) -> OUTPUT_B,
	errorMapper: (errorToMap: ERROR_A) -> ERROR_B,
): Parser<INPUT_A, OUTPUT_B, ERROR_B> {
	return TransformParser(
		parserToMap = parserToMap,
		mapper = mapper,
		errorMapper = errorMapper,
	)
}

/**
 * Ensures the [TransformParser] functions correctly.
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
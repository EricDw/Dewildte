package parsing

class MapParser<USER_STATE, INPUT : Any, OUTPUT_A : Any, OUTPUT_B : Any>(
	private val parserToMap: Parser<USER_STATE, INPUT, OUTPUT_A>,
	private val mapper: (mathedItem: OUTPUT_A) -> OUTPUT_B,
) : Parser<USER_STATE, INPUT, OUTPUT_B> {

	override fun invoke(
		input: Parser.Input<USER_STATE, INPUT>
	): Parser.Result<USER_STATE, INPUT, OUTPUT_B> {
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

infix fun <USER_STATE, INPUT : Any, OUTPUT_A : Any, OUTPUT_B : Any> Parser<USER_STATE, INPUT, OUTPUT_A>.map(
	mapper: (itemToMap: OUTPUT_A) -> OUTPUT_B,
): Parser<USER_STATE, INPUT, OUTPUT_B> {
	return MapParser(
		parserToMap = this,
		mapper = mapper,
	)
}

/**
 * Ensures the [MapParser] functions correctly.
 */
private fun main() {

	val expected: Parser.Result.Match<Unit, Int, String> = Parser.Result.Match(
		nextInput = Parser.Input(
			items = listOf(1),
			userState = Unit,
			position = 1,
		),
		matchedItem = "1",
	)

	val parserToMap: Parser<Unit, Int, Int> = Parser { input ->
		val item = input.items.elementAt(input.position)

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

	val actual: Parser.Result<Unit, Int, String> = runParser(
		userState = Unit,
		itemsToParse = listOf(1),
		parser = parserToMap map Any::toString,
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
package parsing

class SequenceMapParser<USER_STATE, INPUT : Any, OUTPUT_A : Any, OUTPUT_B : Any>(
	private val parsers: Iterable<Parser<USER_STATE, INPUT, OUTPUT_A>>,
	private val mapper: (items: Iterable<OUTPUT_A>) -> OUTPUT_B,
) : AbstractParser<USER_STATE, INPUT, OUTPUT_B>() {

	override fun parse(
		input: Parser.Input<USER_STATE, INPUT>
	): Parser.Result<USER_STATE, INPUT, OUTPUT_B> {

		val parser = SequenceParser(parsers) map mapper

		return parser(input)
	}
}

fun <USER_STATE, INPUT : Any, OUTPUT_A : Any, OUTPUT_B : Any> Iterable<Parser<USER_STATE, INPUT, OUTPUT_A>>.join(
	mapper: (item: Iterable<OUTPUT_A>) -> OUTPUT_B,
): Parser<USER_STATE, INPUT, OUTPUT_B> {
	return SequenceMapParser(
		parsers = this,
		mapper = mapper
	)
}

/**
 * Ensures the [SequenceMapParser] functions correctly.
 */
private fun main() {

	val expected: Parser.Result.Match<Unit, Char, String> = Parser.Result.Match(
		nextInput = Parser.Input(
			items = listOf('A', 'B', 'C'),
			userState = Unit,
			position = 3,
		),
		matchedItem = "ABC",
	)

	val aParser = Parser { input: Parser.Input<Unit, Char> ->
		val item = input.items.elementAt(input.position)

		if (item == 'A') {
			Parser.Result.Match(
				nextInput = input.copy(position = input.position.inc()),
				matchedItem = item,
			)
		} else {
			Parser.Result.Failure(
				originalInput = input,
				error = Throwable("Expected: \'A\', Found: $item")
			)
		}
	}

	val bParser = Parser { input: Parser.Input<Unit, Char> ->
		val item = input.items.elementAt(input.position)

		if (item == 'B') {
			Parser.Result.Match(
				nextInput = input.copy(position = input.position.inc()),
				matchedItem = item,
			)
		} else {
			Parser.Result.Failure(
				originalInput = input,
				error = Throwable("Expected: \'B\', Found: $item")
			)
		}
	}

	val cParser = Parser { input: Parser.Input<Unit, Char> ->
		val item = input.items.elementAt(input.position)

		if (item == 'C') {
			Parser.Result.Match(
				nextInput = input.copy(position = input.position.inc()),
				matchedItem = item,
			)
		} else {
			Parser.Result.Failure(
				originalInput = input,
				error = Throwable("Expected: \'C\', Found: $item")
			)
		}
	}

	val parser: Parser<Unit, Char, String> = listOf(aParser, bParser, cParser).join { items ->
		return@join items.joinToString("")
	}

	val actual: Parser.Result<Unit, Char, String> = runParser(
		userState = Unit,
		itemsToParse = listOf('A', 'B', 'C'),
		parser = parser,
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
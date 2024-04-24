package parsing

class SequenceMapParser<INPUT : Any, OUTPUT_A : Any, OUTPUT_B : Any>(
	private val parsers: Iterable<Parser<INPUT, OUTPUT_A>>,
	private val mapper: (items: Iterable<OUTPUT_A>) -> OUTPUT_B,
) : AbstractParser<INPUT, OUTPUT_B>() {

	override fun parse(
		input: INPUT
	): Parser.Result<INPUT, OUTPUT_B> {

		val parser = SequenceParser(parsers) map mapper

		return parser(input)
	}
}

fun <INPUT : Any, OUTPUT_A : Any, OUTPUT_B : Any> Iterable<Parser<INPUT, OUTPUT_A>>.join(
	mapper: (item: Iterable<OUTPUT_A>) -> OUTPUT_B,
): Parser<INPUT, OUTPUT_B> {
	return SequenceMapParser(
		parsers = this,
		mapper = mapper
	)
}

/**
 * Ensures the [SequenceMapParser] functions correctly.
 */
private fun main() {

	data class CharStream(
		val data: Iterable<Char>,
		val position: Int = 0,
	)

	val expected: Parser.Result.Match<CharStream, String> = Parser.Result.Match(
		nextInput = CharStream(
			data = listOf('A', 'B', 'C'),
			position = 3,
		),
		matchedItem = "ABC",
	)

	val aParser = Parser { input: CharStream ->
		val item = input.data.elementAt(input.position)

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

	val bParser = Parser { input: CharStream ->
		val item = input.data.elementAt(input.position)

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

	val cParser = Parser { input: CharStream ->
		val item = input.data.elementAt(input.position)

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

	val parser: Parser<CharStream, String> = listOf(aParser, bParser, cParser).join { items ->
		return@join items.joinToString("")
	}

	val actual: Parser.Result<CharStream, String> = parser(
		input = CharStream(
			data = listOf('A', 'B', 'C'),
		)
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
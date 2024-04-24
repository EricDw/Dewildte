package parsing

class SequenceParser<INPUT : Any, OUTPUT : Any>(
	private val parsers: Iterable<Parser<INPUT, OUTPUT>>,
) : Parser<INPUT, Iterable<OUTPUT>> {

	override fun invoke(
		input: INPUT
	): Parser.Result<INPUT, Iterable<OUTPUT>> {

		var nextInput = input

		val output: List<OUTPUT> = buildList {
			parsers.forEach { parser ->
				when (val result = parser(nextInput)) {
					is Parser.Result.Failure -> {
						return Parser.Result.Failure(
							originalInput = result.originalInput,
							error = result.error,
						)
					}

					is Parser.Result.Match -> {
						nextInput = result.nextInput
						add(result.matchedItem)
					}
				}
			}
		}

		return Parser.Result.Match(
			nextInput = nextInput,
			matchedItem = output
		)
	}
}

fun <INPUT : Any, OUTPUT : Any> Iterable<Parser<INPUT, OUTPUT>>.join(
): Parser<INPUT, Iterable<OUTPUT>> {
	return SequenceParser(parsers = this)
}

/**
 * Ensures the [SequenceParser] functions correctly.
 */
private fun main() {

	data class CharStream(
		val data: Iterable<Char>,
		val position: Int = 0,
	)

	val expected: Parser.Result.Match<CharStream, Iterable<Char>> = Parser.Result.Match(
		nextInput = CharStream(
			data = listOf('A', 'B', 'C'),
			position = 3,
		),
		matchedItem = listOf('A', 'B', 'C'),
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

	val parser: Parser<CharStream, Iterable<Char>> = listOf(aParser, bParser, cParser).join()

	val actual = parser(
		input = CharStream(
			data = listOf('A', 'B', 'C')
		)
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
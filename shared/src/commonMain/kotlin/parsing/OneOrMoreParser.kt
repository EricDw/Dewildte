package parsing

class OneOrMoreParser<INPUT, OUTPUT, ERROR>(
	private val parser: Parser<INPUT, OUTPUT, ERROR>,
) : Parser<INPUT, Iterable<OUTPUT>, ERROR> {

	override fun invoke(
		input: INPUT
	): Parser.Result<INPUT, Iterable<OUTPUT>, ERROR> {

		var nextInput = input
		var result = parser(input)

		if (result is Parser.Result.Failure) {
			return Parser.Result.Failure(
				result.originalInput,
				result.error,
			)
		}

		val output: List<OUTPUT> = buildList {

			while (result !is Parser.Result.Failure) {
				(result as? Parser.Result.Match)?.let {
					nextInput = it.nextInput
					add(it.matchedItem)
				}
				result = parser(nextInput)
			}
		}

		return Parser.Result.Match(
			nextInput = nextInput,
			matchedItem = output
		)
	}
}

fun <INPUT, OUTPUT, ERROR> oneOrMore(
	parser: Parser<INPUT, OUTPUT, ERROR>
): Parser<INPUT, Iterable<OUTPUT>, ERROR> {
	return OneOrMoreParser(parser = parser)
}

/**
 * Ensures the [OneOrMoreParser] functions correctly.
 */
private fun main() {

	data class CharStream(
		val data: Iterable<Char>,
		val position: Int = 0,
	)

	val expected: Parser.Result.Match<CharStream, Iterable<Char>, Throwable> = Parser.Result.Match(
		nextInput = CharStream(
			data = listOf('A', 'A', 'A', 'B', 'C'),
			position = 4,
		),
		matchedItem = listOf('A', 'A', 'A', 'B'),
	)

	val aParser = Parser { input: CharStream ->
		val item = input.data.elementAt(input.position)

		if (item == 'A' || item == 'B') {
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


	val parser = oneOrMore(aParser)

	val actual = parser(
		input = CharStream(
			data = listOf('A', 'A', 'A', 'B', 'C')
		)
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
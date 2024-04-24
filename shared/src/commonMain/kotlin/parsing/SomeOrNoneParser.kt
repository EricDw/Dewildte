package parsing

class SomeOrNoneParser<INPUT : Any, OUTPUT : Any, ERROR: Throwable>(
	private val parser: Parser<INPUT, OUTPUT, ERROR>,
) : Parser<INPUT, Iterable<OUTPUT>, ERROR> {

	override fun invoke(
		input: INPUT
	): Parser.Result<INPUT, Iterable<OUTPUT>, ERROR> {

		var nextInput = input
		var result = parser(input)

		val output: List<OUTPUT> = buildList {
			while (result !is Parser.Result.Failure) {
				result = parser(nextInput)
				(result as? Parser.Result.Match)?.let {
					nextInput = it.nextInput
					add(it.matchedItem)
				}
			}
		}

		return Parser.Result.Match(
			nextInput = nextInput,
			matchedItem = output
		)
	}
}

fun <INPUT : Any, OUTPUT : Any, ERROR: Throwable> someOrNone(
	parser: Parser<INPUT, OUTPUT, ERROR>
): Parser<INPUT, Iterable<OUTPUT>, ERROR> {
	return SomeOrNoneParser(parser = parser)
}

/**
 * Ensures the [SomeOrNoneParser] functions correctly.
 */
private fun main() {

	data class CharStream(
		val data: Iterable<Char>,
		val position: Int = 0,
	)

	val expected: Parser.Result.Match<CharStream, Iterable<Char>, Throwable> = Parser.Result.Match(
		nextInput = CharStream(
			data = listOf('A', 'A', 'A', 'B', 'C'),
			position = 3,
		),
		matchedItem = listOf('A', 'A', 'A'),
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


	val parser = someOrNone(aParser)

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
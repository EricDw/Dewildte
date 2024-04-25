package parsing

class ChoiceParser<INPUT, OUTPUT, ERROR>(
	private val parsers: Iterable<Parser<INPUT, OUTPUT, ERROR>>,
) : Parser<INPUT, OUTPUT, ERROR> {

	override fun invoke(
		input: INPUT
	): Parser.Result<INPUT, OUTPUT, ERROR> {

		val parser = parsers.reduce(::OrParser)

		return parser(input)

	}
}

fun <INPUT, OUTPUT, ERROR> Iterable<Parser<INPUT, OUTPUT, ERROR>>.toChoice(
): Parser<INPUT, OUTPUT, ERROR> {
	return choice(parsers = this)
}

fun <INPUT, OUTPUT, ERROR> choice(
	parsers: Iterable<Parser<INPUT, OUTPUT, ERROR>>
): Parser<INPUT, OUTPUT, ERROR> {
	return ChoiceParser(parsers = parsers)
}

fun <INPUT, OUTPUT, ERROR> choiceOf(
	vararg parsers: Parser<INPUT, OUTPUT, ERROR>
): Parser<INPUT, OUTPUT, ERROR> {
	return parsers.toList().toChoice()
}

/**
 * Ensures the [ChoiceParser] functions correctly.
 */
private fun main() {

	data class CharStream(
		val data: Iterable<Char>,
		val position: Int = 0,
	)

	val expected: Parser.Result.Match<CharStream, Char, Throwable> = Parser.Result.Match(
		nextInput = CharStream(
			data = listOf('A', 'B', 'C'),
			position = 1,
		),
		matchedItem = 'A',
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

	val parser = choiceOf(aParser, bParser, cParser)

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
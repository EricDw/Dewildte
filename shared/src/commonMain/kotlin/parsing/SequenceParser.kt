package parsing

class SequenceParser<USER_STATE, INPUT : Any, OUTPUT : Any>(
	private val parsers: Iterable<Parser<USER_STATE, INPUT, OUTPUT>>,
) : Parser<USER_STATE, INPUT, Iterable<OUTPUT>> {

	override fun invoke(
		input: Parser.Input<USER_STATE, INPUT>
	): Parser.Result<USER_STATE, INPUT, Iterable<OUTPUT>> {

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

fun <USER_STATE, INPUT : Any, OUTPUT : Any> Iterable<Parser<USER_STATE, INPUT, OUTPUT>>.join(
): Parser<USER_STATE, INPUT, Iterable<OUTPUT>> {
	return SequenceParser(parsers = this)
}

/**
 * Ensures the [SequenceParser] functions correctly.
 */
private fun main() {

	val expected: Parser.Result.Match<Unit, Char, List<Char>> = Parser.Result.Match(
		nextInput = Parser.Input(
			items = listOf('A', 'B', 'C'),
			userState = Unit,
			position = 3,
		),
		matchedItem = listOf('A', 'B', 'C'),
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
	
	val parser: Parser<Unit, Char, Iterable<Char>> = listOf(aParser, bParser, cParser).join()
	
	val actual: Parser.Result<Unit, Char, Iterable<Char>> = runParser(
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
package parsing

class NotParser<INPUT>(
	private val delegate: Parser<INPUT, *, *>,
) : Parser<INPUT, Unit, Unit> {

	override fun invoke(
		input: INPUT
	): Parser.Result<INPUT, Unit, Unit> {

		return when (val result = delegate(input)) {
			is Parser.Result.Failure -> {
				Parser.Result.Match(input, Unit)
			}

			is Parser.Result.Match -> {
				Parser.Result.Failure(input, Unit)
			}
		}

	}
}

fun <INPUT> not(
	delegate: Parser<INPUT, *, *>
): Parser<INPUT, Unit, Unit> {
	return NotParser(delegate = delegate)
}

/**
 * Ensures the [NotParser] functions correctly.
 */
private fun main() {

	val expected: Parser.Result.Failure<String, Unit, Unit> = Parser.Result.Failure(
		originalInput = "A",
		error = Unit,
	)

	val parser = not { input: String ->
		if (input == "A") {
			Parser.Result.Match(input, "A")
		} else {
			Parser.Result.Failure(input, Throwable("Expected: \"A\", Found: $input"))
		}
	}

	val actual = parser(
		input = "A"
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
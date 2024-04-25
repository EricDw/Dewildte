package parsing

class JustParser<INPUT, OUTPUT, ERROR>(
	private val output: OUTPUT,
) : Parser<INPUT, OUTPUT, ERROR> {

	override fun invoke(
		input: INPUT
	): Parser.Result<INPUT, OUTPUT, ERROR> {

		return Parser.Result.Match(nextInput = input, matchedItem = output)

	}
}

fun <INPUT, OUTPUT, ERROR> OUTPUT.toJust(): Parser<INPUT, OUTPUT, ERROR> {
	return JustParser(output = this)
}

/**
 * Ensures the [JustParser] functions correctly.
 */
private fun main() {

	val expected: Parser.Result.Match<String, String, Throwable> = Parser.Result.Match(
		nextInput = "A",
		matchedItem = "B",
	)

	val parser = "B".toJust<String, String, Throwable>()

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
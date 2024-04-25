package parsing

fun <INPUT, OUTPUT_A, OUTPUT_B, ERROR> lift(
	functionToLift: (OUTPUT_A) -> OUTPUT_B
): (Parser<INPUT, OUTPUT_A, ERROR>) -> Parser<INPUT, OUTPUT_B, ERROR> {
	return { parser -> parser map functionToLift }
}

/**
 * Ensures the [lift] functions correctly.
 */
private fun main() {

	val expected = Parser.Result.Match<String, Int, Throwable>(
		nextInput = "Hello",
		matchedItem = 5,
	)

	val functionToLift = { input: String ->
		input.length
	}

	val liftedFunction =
		lift<String, String, Int, Throwable>(functionToLift)

	val parser: Parser<String, Int, Throwable> = liftedFunction { input ->
		Parser.Result.Match(input, input)
	}

	val actual =
		parser(input = "Hello")

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
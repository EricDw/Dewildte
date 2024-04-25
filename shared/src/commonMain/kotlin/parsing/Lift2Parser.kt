package parsing

class Lift2Parser<INPUT, OUTPUT_A, OUTPUT_B, OUTPUT_C, ERROR>(
	private val functionToLift: (OUTPUT_A, OUTPUT_B) -> OUTPUT_C,
	private val parserA: Parser<INPUT, OUTPUT_A, ERROR>,
	private val parserB: Parser<INPUT, OUTPUT_B, ERROR>,
) : Parser<INPUT, OUTPUT_C, ERROR> {

	override fun invoke(
		input: INPUT
	): Parser.Result<INPUT, OUTPUT_C, ERROR> {

		val parser =
			(parserA and parserB) map { (a, b) ->
				functionToLift(a, b)
			}

		return parser(input)

	}

}

fun <INPUT, OUTPUT_A, OUTPUT_B, OUTPUT_C, ERROR> lift2(
	functionToLift: (OUTPUT_A, OUTPUT_B) -> OUTPUT_C,
): (Parser<INPUT, OUTPUT_A, ERROR>, Parser<INPUT, OUTPUT_B, ERROR>) -> Parser<INPUT, OUTPUT_C, ERROR> {
	return { parserA, parserB ->
		Lift2Parser(
			functionToLift = functionToLift,
			parserA = parserA,
			parserB = parserB,
		)
	}
}

/**
 * Ensures the [Lift2Parser] functions correctly.
 */
private fun main() {

	val expected: Parser.Result.Match<Iterable<String>, String, Throwable> = Parser.Result.Match(
		nextInput = listOf("Hello", ", World"),
		matchedItem = "Hello, World",
	)

	val firstApplicantParser: Parser<Iterable<String>, String, Throwable> = Parser { input ->
		Parser.Result.Match(input, input.first())
	}

	val secondApplicantParser: Parser<Iterable<String>, String, Throwable> = Parser { input ->
		Parser.Result.Match(input, input.elementAt(1))
	}

	val liftedFunction =
		lift2<Iterable<String>, String, String, String, Throwable>(functionToLift = String::plus)

	val parser = liftedFunction(
		firstApplicantParser, secondApplicantParser
	)

	val actual = parser(listOf("Hello", ", World"))

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
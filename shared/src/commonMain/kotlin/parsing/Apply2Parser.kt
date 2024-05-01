package parsing

class Apply2Parser<INPUT, OUTPUT_A, OUTPUT_B, OUTPUT_C, ERROR>(
	private val parserToApply: Parser<INPUT, (OUTPUT_A, OUTPUT_B) -> OUTPUT_C, ERROR>,
	private val applicantParser: Parser<INPUT, Pair<OUTPUT_A, OUTPUT_B>, ERROR>,
) : Parser<INPUT, OUTPUT_C, ERROR> {

	override fun invoke(
		input: INPUT
	): Parser.Result<INPUT, OUTPUT_C, ERROR> {

		val parser =
			(parserToApply and applicantParser) map { (f, x) ->
				val (a, b) = x
				
				f(a, b)
			}

		return parser(input)

	}

}

infix fun <INPUT, OUTPUT_A, OUTPUT_B, OUTPUT_C, ERROR> Parser<INPUT, (OUTPUT_A, OUTPUT_B) -> OUTPUT_C, ERROR>.apply(
	applicantParser: Parser<INPUT, Pair<OUTPUT_A, OUTPUT_B>, ERROR>,
): Parser<INPUT, OUTPUT_C, ERROR> {
	return Apply2Parser(
		parserToApply = this,
		applicantParser = applicantParser,
	)
}

/**
 * Ensures the [Apply2Parser] functions correctly.
 */
private fun main() {

	val expected: Parser.Result.Match<String, Int, Throwable> = Parser.Result.Match(
		nextInput = "44",
		matchedItem = 8,
	)

	val applicantParser: Parser<String, Pair<Int, Int>, Throwable> = Parser { input: String ->
		Parser.Result.Match(input, input[0].digitToInt() to input[1].digitToInt())
	}

	val parserToApply: Parser<String, (Int, Int) -> Int, Throwable> = Parser { input: String ->
		Parser.Result.Match(nextInput = input, Int::plus)
	}

	val parser = parserToApply apply applicantParser

	val actual: Parser.Result<String, Int, Throwable> = parser(
		input = "44",
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
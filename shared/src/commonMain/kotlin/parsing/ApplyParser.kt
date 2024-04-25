package parsing

class ApplyParser<INPUT, OUTPUT_A, OUTPUT_B, ERROR>(
	private val parserToApply: Parser<INPUT, (OUTPUT_A) -> OUTPUT_B, ERROR>,
	private val applicantParser: Parser<INPUT, OUTPUT_A, ERROR>,
) : Parser<INPUT, OUTPUT_B, ERROR> {

	override fun invoke(
		input: INPUT
	): Parser.Result<INPUT, OUTPUT_B, ERROR> {

		val parser =
			(parserToApply and applicantParser) map { (f, x) ->
				f(x)
			}

		return parser(input)

	}

}

infix fun <INPUT, OUTPUT_A, OUTPUT_B, ERROR> Parser<INPUT, (OUTPUT_A) -> OUTPUT_B, ERROR>.apply(
	applicantParser: Parser<INPUT, OUTPUT_A, ERROR>,
): Parser<INPUT, OUTPUT_B, ERROR> {
	return ApplyParser(
		parserToApply = this,
		applicantParser = applicantParser,
	)
}

/**
 * Ensures the [ApplyParser] functions correctly.
 */
private fun main() {
	
	val expected: Parser.Result.Match<String, Char, Throwable> = Parser.Result.Match(
		nextInput = "ABC",
		matchedItem = 'A',
	)
	
	val applicantParser: Parser<String, Int, Throwable> = Parser { input: String ->
		Parser.Result.Match(input, input.first().code)
	}

	val parserToApply: Parser<String, (Int) -> Char, Throwable> = Parser { input: String ->
		Parser.Result.Match(nextInput = input, ::Char)
	}

	val parser = parserToApply apply applicantParser

	val actual: Parser.Result<String, Char, Throwable> = parser(
		input = "ABC",
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
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
	
	val expected: Parser.Result.Match<Int, Char, Throwable> = Parser.Result.Match(
		nextInput = 'A'.code,
		matchedItem = 'A',
	)
	
	val applicantParser: Parser<Int, Int, Throwable> = Parser { input: Int ->
		Parser.Result.Match(input, input)
	}

	val parserToApply: Parser<Int, (Int) -> Char, Throwable> = Parser { input: Int ->
		Parser.Result.Match(nextInput = input, ::Char)
	}

	val parser = parserToApply apply applicantParser

	val actual: Parser.Result<Int, Char, Throwable> = parser(
		input = 'A'.code,
	)

	check(
		value = actual == expected
	) {
		"\n\tExpected: $expected\n\tActual: $actual"
	}

	println("Success")
}
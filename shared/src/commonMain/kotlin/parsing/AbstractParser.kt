package parsing

abstract class AbstractParser<USER_STATE, INPUT : Any, OUTPUT : Any> : Parser<USER_STATE, INPUT, OUTPUT> {

	override fun invoke(
		input: Parser.Input<USER_STATE, INPUT>
	): Parser.Result<USER_STATE, INPUT, OUTPUT> {
		return try {
			parse(input)
		} catch (error: Throwable) {
			fail(
				originalInput = input,
				error = error,
			)
		}
	}

	abstract fun parse(
		input: Parser.Input<USER_STATE, INPUT>
	): Parser.Result<USER_STATE, INPUT, OUTPUT>

	fun match(
		originalInput: Parser.Input<USER_STATE, INPUT>,
		output: OUTPUT
	): Parser.Result.Match<USER_STATE, INPUT, OUTPUT> {
		val nextInput = originalInput.copy(
			position = originalInput.position.inc()
		)

		return Parser.Result.Match(
			nextInput = nextInput,
			matchedItem = output,
		)
	}

	fun fail(
		originalInput: Parser.Input<USER_STATE, INPUT>,
		error: Throwable,
	): Parser.Result.Failure<USER_STATE, INPUT, OUTPUT> {
		return Parser.Result.Failure(
			originalInput = originalInput,
			error = error,
		)
	}

}

class SampleParser : AbstractParser<Unit, Int, Int>() {
	override fun parse(input: Parser.Input<Unit, Int>): Parser.Result<Unit, Int, Int> {
		return match(input, 1)
	}

}

/**
* Ensures the [AbstractParser] functions correctly.
*/
private fun main() {

	val expectedA: Parser.Result.Match<Unit, Int, Int> = Parser.Result.Match(
		nextInput = Parser.Input(
			items = listOf(0),
			userState = Unit,
			position = 1,
		),
		matchedItem = 0,
	)

	val expectedB = "Empty list doesn't contain element at index 0."

	val parser = object : AbstractParser<Unit, Int, Int>() {
		override fun parse(input: Parser.Input<Unit, Int>): Parser.Result<Unit, Int, Int> {
			return match(
				originalInput = input,
				output = input.items.elementAt(input.position)
			)
		}

	}

	val actualA: Parser.Result<Unit, Int, Int> = runParser(
		userState = Unit,
		itemsToParse = listOf(0),
		parser = parser
	)

	val actualB = (runParser(
		userState = Unit,
		itemsToParse = listOf(),
		parser = parser
	) as Parser.Result.Failure).error.message

	check(
		value = actualA == expectedA
	) {
		"\n\tExpected: $expectedA\n\tActual: $actualA"
	}

	check(
		value = actualB == expectedB
	) {
		"\n\tExpected: $expectedB\n\tActual: $actualB"
	}

	println("Success")
}
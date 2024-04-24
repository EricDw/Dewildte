package parsing

import parsing.Parser.*

abstract class AbstractParser<INPUT : Any, OUTPUT : Any> : Parser<INPUT, OUTPUT> {

	override fun invoke(
		input: INPUT
	): Result<INPUT, OUTPUT> {
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
		input: INPUT
	): Result<INPUT, OUTPUT>

	fun match(
		originalInput: INPUT,
		output: OUTPUT
	): Result.Match<INPUT, OUTPUT> {
		return Result.Match(
			nextInput = originalInput,
			matchedItem = output,
		)
	}

	fun fail(
		originalInput: INPUT,
		error: Throwable,
	): Result.Failure<INPUT, OUTPUT> {
		return Result.Failure(
			originalInput = originalInput,
			error = error,
		)
	}

}

/**
 * Ensures the [AbstractParser] functions correctly.
 */
private fun main() {

	data class IntStream(
		val position: Int = 0,
		val data: Iterable<Int> = listOf(0),
	)

	val expectedA: Result.Match<IntStream, Int> = Result.Match(
		nextInput = IntStream(),
		matchedItem = 0,
	)

	val expectedB = "Empty list doesn't contain element at index 0."

	val parser = object : AbstractParser<IntStream, Int>() {
		override fun parse(input: IntStream): Result<IntStream, Int> {
			return match(
				originalInput = input,
				output = input.data.elementAt(0)
			)
		}

	}

	val actualA = parser(input = IntStream())

	val actualB = (parser(
		input = IntStream(data = listOf())
	) as Result.Failure).error.message

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
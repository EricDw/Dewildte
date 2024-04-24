package parsing

/**
 * Defines the basic contract for parsers.
 *
 * A parser determines how to extract, or parse,
 * an object of type [OUTPUT] out of an object of type [INPUT].
 */
fun interface Parser<INPUT : Any, OUTPUT : Any> {

	sealed class Result<INPUT : Any, OUTPUT : Any> {
		data class Match<INPUT : Any, OUTPUT : Any>(
			val nextInput: INPUT,
			val matchedItem: OUTPUT,
		) : Result<INPUT, OUTPUT>()

		data class Failure<INPUT : Any, OUTPUT : Any>(
			val originalInput: INPUT,
			val error: Throwable,
		) : Result<INPUT, OUTPUT>()
	}

	operator fun invoke(
		input: INPUT
	): Result<INPUT, OUTPUT>
}
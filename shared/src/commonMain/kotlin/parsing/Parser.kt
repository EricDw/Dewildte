package parsing

/**
 * Defines the basic contract for parsers.
 *
 * A parser determines how to extract, or parse,
 * an object of type [OUTPUT] out of an object of type [INPUT].
 */
fun interface Parser<INPUT, OUTPUT, ERROR> {

	sealed class Result<INPUT, OUTPUT, ERROR> {

		data class Match<INPUT, OUTPUT, ERROR>(
			val nextInput: INPUT,
			val matchedItem: OUTPUT,
		) : Result<INPUT, OUTPUT, ERROR>()

		data class Failure<INPUT, OUTPUT, ERROR>(
			val originalInput: INPUT,
			val error: ERROR,
		) : Result<INPUT, OUTPUT, ERROR>()
	}

	operator fun invoke(
		input: INPUT
	): Result<INPUT, OUTPUT, ERROR>
}
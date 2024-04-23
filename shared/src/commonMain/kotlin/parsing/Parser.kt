package parsing

fun interface Parser<USER_STATE, INPUT : Any, OUTPUT: Any> {

	data class Input<USER_STATE, INPUT : Any>(
		val items: Iterable<INPUT>,
		val userState: USER_STATE,
		val position: Int = 0,
	)

	sealed class Result<USER_STATE, INPUT : Any, OUTPUT : Any> {
		data class Match<USER_STATE, INPUT : Any, OUTPUT : Any>(
			val nextInput: Input<USER_STATE, INPUT>,
			val matchedItem: OUTPUT,
		) : Result<USER_STATE, INPUT, OUTPUT>()

		data class Failure<USER_STATE, INPUT : Any, OUTPUT : Any>(
			val originalInput: Input<USER_STATE, INPUT>,
			val error: Throwable,
		) : Result<USER_STATE, INPUT, OUTPUT>()
	}

	operator fun invoke(input: Input<USER_STATE, INPUT>): Result<USER_STATE, INPUT, OUTPUT>
}
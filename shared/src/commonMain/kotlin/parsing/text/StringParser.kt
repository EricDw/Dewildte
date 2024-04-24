package parsing

class StringParser(
	private val stringToMatch: String,
	private val ignoreCase: Boolean = false,
) : Parser<String, String> {
	override fun invoke(
		input: String
	): Parser.Result<String, String> {

//		var result: Parser.Result<String>? = null
//
//		return when (predicate(item)) {
//			true -> {
//				val output = input.copy(
//					position = input.position.inc()
//				)
//				Parser.Result.Match(
//					nextInput = output,
//					matchedItem = item,
//				)
//			}
//
//			false -> {
//				Parser.Result.Failure(
//					originalInput = input,
//					error = "Item: $item, Failed to match the predicate: $predicate",
//				)
//			}
//		}

		TODO()
	}

}
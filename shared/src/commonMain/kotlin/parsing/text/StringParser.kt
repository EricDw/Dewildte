package parsing

class StringParser<USER_STATE>(
	private val stringToMatch: String,
	private val ignoreCase: Boolean = false,
) : Parser<USER_STATE, String, String> {
	override fun invoke(
		input: Parser.Input<USER_STATE, String>
	): Parser.Result<USER_STATE, String, String> {

//		var result: Parser.Result<USER_STATE, String>? = null
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
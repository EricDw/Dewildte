package parsing

fun main() {
	val parser: Parser<Unit, Char, Char> = PredicateParser { char ->
		char == 'A'
	}
	
	val input: Parser.Input<Unit, Char> = Parser.Input(
		items = "a".toList(),
		userState = Unit,
	)
	
	val result: Parser.Result<Unit, Char, Char> = parser.invoke(input = input)
	
	print("Result: $result")
}
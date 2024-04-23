package parsing

fun <USER_STATE, INPUT : Any, OUTPUT : Any> runParser(
	userState: USER_STATE,
	itemsToParse: Iterable<INPUT>,
	position: Int = 0,
	parser: Parser<USER_STATE, INPUT, OUTPUT>,
): Parser.Result<USER_STATE, INPUT, OUTPUT> {
	val input: Parser.Input<USER_STATE, INPUT> = Parser.Input(
		items = itemsToParse,
		userState = userState,
		position = position,
	)

	return parser(input)
}

fun <USER_STATE, INPUT : Any, OUTPUT : Any> runParser(
	userState: USER_STATE,
	itemsToParse: Iterable<INPUT>,
	parser: Parser<USER_STATE, INPUT, OUTPUT>,
): Parser.Result<USER_STATE, INPUT, OUTPUT> {
	return runParser(
		userState = userState,
		itemsToParse = itemsToParse,
		position = 0,
		parser = parser,
	)
}
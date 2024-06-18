package parsing.markdown.lexer

private fun main() {

	val lexer = MarkdownLexer()

	val markdown =
		"Hello, __*World*__!"

	val tokens =
		lexer.analyzeMarkdown(markdown)

	println("Tokens:")
	tokens.forEach(::println)
	println()
	println(tokens.joinToString("") { it.value })
	check(tokens.size == 7) {
		"Expected: 7 tokens, found: ${tokens.size}"
	}

}
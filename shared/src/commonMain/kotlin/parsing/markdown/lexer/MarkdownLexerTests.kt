package parsing.markdown.lexer

private fun main() {

	val lexer = MarkdownLexer()

	val markdown =
		"Hello, _*World*_!"

	val tokens =
		lexer.analyzeMarkdown(markdown)

	println("Tokens:")
	tokens.forEach(::println)
	println()
	println(tokens.joinToString("") { it.value })

}
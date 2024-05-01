package parsing.markdown

import parsing.Parser
import parsing.markdown.lexer.EmphasisMarkdownTextParser
import parsing.markdown.lexer.PlainTextMarkdownTextParser
import parsing.oneOrMore
import parsing.or

private fun main() {

	val plainText =
		PlainTextMarkdownTextParser()

	val emphasis =
		EmphasisMarkdownTextParser()

	val parser =
		oneOrMore(emphasis or plainText)

	val markdown =
		"Hello, _*World*_!"

	val input =
		MarkdownParserState(
			markdown = markdown,
		)

	val result =
		parser(input = input)

	println(result)
	println()
	val tokens = (result as Parser.Result.Match).matchedItem
	tokens.forEach(::println)
	println()
	println(tokens.joinToString("") { it.value })

}
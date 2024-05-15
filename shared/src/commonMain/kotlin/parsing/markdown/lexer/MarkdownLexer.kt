package parsing.markdown.lexer

import parsing.Parser
import parsing.markdown.MarkdownParserError
import parsing.markdown.MarkdownParserState
import parsing.oneOrMore
import parsing.or

class MarkdownLexer {

	@Throws(MarkdownParserError::class)
	fun analyzeMarkdown(
		markdown: String
	): List<MarkdownToken> {

		val plainText =
			PlainTextMarkdownTextParser()

		val emphasis =
			EmphasisMarkdownTextParser()

		val parser =
			oneOrMore(emphasis or plainText)

		val input =
			MarkdownParserState(
				markdown = markdown,
			)

		val result =
			parser(input)

		return when (result) {
			is Parser.Result.Failure -> {
				throw result.error
			}

			is Parser.Result.Match -> {
				result.matchedItem.toList()
			}
		}

	}

}
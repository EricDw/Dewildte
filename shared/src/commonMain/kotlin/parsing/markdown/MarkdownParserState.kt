package parsing.markdown

import parsing.markdown.lexer.MarkdownToken

data class MarkdownParserState(
	val markdown: String,
	val position: Int = 0,
	val tokens: List<MarkdownToken> = emptyList(),
)
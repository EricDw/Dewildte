package parsing.markdown.scanner

import parsing.markdown.MarkdownParserState
import parsing.markdown.lexer.MarkdownToken
import parsing.markdown.lexer.PlainTextMarkdownTextParser
import parsing.someOrNone

class RealMarkdownScanner : MarkdownScanner {

    private val plainTextParser = PlainTextMarkdownTextParser()

    override fun scan(markdown: String): List<MarkdownToken> {

        if (markdown.isEmpty()) {
            return emptyList()
        }

        val parser =
            someOrNone(plainTextParser)

        val input =
            MarkdownParserState(markdown = markdown)

        val result =
            parser(input = input)

        return when (result) {
            is parsing.Parser.Result.Failure -> {
                emptyList()
            }

            is parsing.Parser.Result.Match -> {
                result.matchedItem.toList()
            }
        }
    }
}
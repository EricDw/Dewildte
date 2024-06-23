package parsing.markdown.lexer

import parsing.*
import parsing.markdown.MarkdownParserError
import parsing.markdown.MarkdownParserState

class MarkdownLexer {

    @Throws(MarkdownParserError::class)
    fun analyzeMarkdown(
        markdown: String
    ): List<MarkdownToken> {

        if (markdown.isEmpty()) {
            return emptyList()
        }

        val plainText =
            PlainTextMarkdownTextParser()

        val emphasis =
            EmphasisMarkdownTextParser()

        val strong =
            StrongMarkdownTextParser()

        val codeSpan =
            CodeSpanMarkdownTextParser()

        val basicBullet =
            BasicBulletPointMarkdownTextParser()

        val choices = choiceOf(
            strong,
            emphasis,
            codeSpan,
            basicBullet,
            plainText
        )

        val parser =
            oneOrMore(choices)

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
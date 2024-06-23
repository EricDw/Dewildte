package parsing.markdown.lexer

import parsing.Parser
import parsing.map
import parsing.markdown.MarkdownParserError
import parsing.markdown.MarkdownParserState
import parsing.markdown.MarkdownTextParser
import parsing.text.StringParser
import parsing.text.TextParserState

class BasicBulletPointMarkdownTextParser : MarkdownTextParser<MarkdownToken> {

	override fun invoke(
		input: MarkdownParserState
	): Parser.Result<MarkdownParserState, MarkdownToken, MarkdownParserError> {

		val (markdown, position, tokens) =
			input

		val parser =
			StringParser("-") map { value ->

                MarkdownToken.BasicBulletPoint(
                    value = value,
                    positionStart = position,
                    positionEnd = position,
                )
			}

		val textParserState =
            TextParserState(
                text = markdown,
                position = position
            )

		val result =
			try {
				parser(textParserState)

			} catch (error: Throwable) {
				return Parser.Result.Failure(
                    originalInput = input,
                    error = MarkdownParserError(
                        message = error.message ?: "Unexpected error",
                        cause = error,
                    )
                )
			}

		return when (result) {
			is Parser.Result.Failure -> {
                Parser.Result.Failure(
                    originalInput = input,
                    error = MarkdownParserError(
                        result.error.message,
                        cause = result.error,
                    )
                )
			}

			is Parser.Result.Match -> {
				val token = result.matchedItem

				val newTokens = tokens + token

				val nextInput = input.copy(
					position = token.positionEnd.inc(),
					tokens = newTokens,
				)

                Parser.Result.Match(
                    nextInput = nextInput,
                    matchedItem = token,
                )
			}
		}
	}

}
package parsing.markdown.lexer

import parsing.Parser
import parsing.map
import parsing.markdown.MarkdownParserError
import parsing.markdown.MarkdownParserState
import parsing.markdown.MarkdownTextParser
import parsing.or
import parsing.text.StringParser
import parsing.text.TextParserState

class StrongMarkdownTextParser : MarkdownTextParser<MarkdownToken> {

	override fun invoke(
		input: MarkdownParserState
	): Parser.Result<MarkdownParserState, MarkdownToken, MarkdownParserError> {

		val (markdown, position, tokens) =
			input

		val underscoresParser =
			StringParser("__") map {
				it to MarkdownToken.Emphasis.Type.BOLD
			}

		val asterisksParser =
			StringParser("**") map {
				it to MarkdownToken.Emphasis.Type.BOLD
			}

		val typeParser =
			underscoresParser or asterisksParser

		val parser =
			typeParser map { (value, type) ->

                MarkdownToken.Emphasis(
                    value = value,
                    positionStart = position,
                    positionEnd = position.inc(),
                    type = type,
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

private fun main() {

	val parser =
		StrongMarkdownTextParser()

	val input =
		MarkdownParserState(
			markdown = "__World__"
		)

	val result =
		parser(input)

	println(result)

}
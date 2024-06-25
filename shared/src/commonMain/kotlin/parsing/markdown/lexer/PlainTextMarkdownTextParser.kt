package parsing.markdown.lexer

import parsing.*
import parsing.markdown.MarkdownParserError
import parsing.markdown.MarkdownParserState
import parsing.markdown.MarkdownTextParser
import parsing.text.*

class PlainTextMarkdownTextParser : MarkdownTextParser<MarkdownToken> {

	override fun invoke(
		input: MarkdownParserState
	): Parser.Result<MarkdownParserState, MarkdownToken, MarkdownParserError> {
		
		val (markdown, position, tokens) =
			input
		
		val invalidCharacters =
			setOf(
				'_',
				'*',
				'`',
				'-',
			)

		val validChar = PredicateTextParser { char ->
			!char.isWhitespace() &&
			!invalidCharacters.contains(char)
		}

		val plainText =
			oneOrMore(validChar) map { chars->
				chars.joinToString(separator = "")
			}

		val parser =
			plainText map { text ->
				val positionEnd = if (text.length == 1) {
					position.inc()
				} else {
					position + text.length.dec()
				}

				MarkdownToken.PlainTextSpan(
					value = text,
					positionStart = position,
					positionEnd = positionEnd
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
				
				val nextPosition = if (token.value.length == 1) {
					position.inc()
				} else {
					token.positionEnd.inc()
				}

				val nextInput = input.copy(
					position = nextPosition,
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
		PlainTextMarkdownTextParser()

	val input =
		MarkdownParserState(
			markdown = "Hello, _World_"
		)

	val result =
		parser(input)

	println(result)

}
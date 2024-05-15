package design.text

import androidx.compose.ui.text.AnnotatedString
import parsing.*
import parsing.text.CharParser
import parsing.text.PredicateTextParser
import parsing.text.StringParser
import parsing.text.TextParserState

data class MarkdownState(
	val markdown: String,
	val annotatedString: AnnotatedString = AnnotatedString(""),
	val position: Int = 0,
)


class BoldMarkdownParser : Parser<MarkdownState, MarkdownState, Throwable> {

	override fun invoke(
		input: MarkdownState
	): Parser.Result<MarkdownState, MarkdownState, Throwable> {
		val nextState = input.copy(
			position = 0
		)

		val textInput = TextParserState(
			text = input.markdown,
		)

		val underscore = CharParser('_')
		val bold = StringParser("bold")
		val space = CharParser(' ')
		val underscoreOrSpace = underscore or space

		val between = between(underscore, bold, underscore)

		val textResult = between(textInput)

		val parser = underscore

		val result = parser(textInput)

		// underscore and 1 or more (not (underscore or space)) and underscore


		return Parser.Result.Match(
			nextInput = nextState,
			matchedItem = nextState
		)
	}

}

private fun main() {

	val markdown = """_bold_"""
""
	val input = TextParserState(markdown)

	val underscore = CharParser('_')
	val bold = StringParser("bold")

	val parser = between(underscore, bold, underscore)

	val result = parser.invoke(input)

	println(result)

}
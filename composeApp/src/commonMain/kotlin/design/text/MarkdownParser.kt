package design.text

import androidx.compose.ui.text.AnnotatedString
import parsing.*
import parsing.text.CharParser

data class MarkdownState(
	val markdown: String,
	val annotatedString: AnnotatedString = AnnotatedString(""),
	val position: Int = 0,
)


class BoldMarkdownParser : AbstractParser<MarkdownState, MarkdownState>() {
	override fun parse(
		input: MarkdownState
	): Parser.Result<MarkdownState, MarkdownState> {
		val nextState = input.copy(
			position = 0
		)

		val underscore = CharParser('_')
		val space = CharParser(' ')
		val underscoreOrSpace = underscore or space

		val between = underscore and_ space _and underscore

		val notUnderscore: PredicateParser<Iterable<Char>> = PredicateParser { chars ->
			val char = chars.first()
			char != '_'
		}

		val parser = underscore

		val result = parser(input.markdown)

		// underscore and 1 or more (not (underscore or space)) and underscore


		return match(
			originalInput = input,
			output = nextState
		)
	}

}
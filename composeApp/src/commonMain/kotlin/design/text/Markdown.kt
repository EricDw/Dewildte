package design.text

import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.ui.tooling.preview.Preview
import parsing.markdown.lexer.MarkdownLexer
import parsing.markdown.lexer.MarkdownToken

@Composable
fun Markdown(
	value: String
) {
	val lexer = MarkdownLexer()

	val tokens = lexer.analyzeMarkdown(value)

	val annotatedStrings = tokens.map { token ->
		when (token) {
			is MarkdownToken.Emphasis -> {
				buildAnnotatedString {
					append(token.value)
					pushStyle(SpanStyle())
				}

			}

			is MarkdownToken.Heading -> {
				buildAnnotatedString {
					append(token.value)
				}
			}

			is MarkdownToken.PlainTextSpan -> {
				buildAnnotatedString {
					append(token.value)
				}
			}
		}
	}

	val text =
		buildAnnotatedString {

			var boldIndex: Int? = null
			var italicIndex: Int? = null

			tokens.forEach { token ->
				when (token) {
					is MarkdownToken.Emphasis -> {
						when (token.type) {
							MarkdownToken.Emphasis.Type.BOLD -> {
								if (boldIndex == null) {
									boldIndex = pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
								} else {
									pop(boldIndex!!)
									boldIndex = null
								}
							}

							MarkdownToken.Emphasis.Type.ITALIC -> {
								if (italicIndex == null) {
									italicIndex = pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
								} else {
									pop(italicIndex!!)
									italicIndex = null
								}
							}
						}
					}

					is MarkdownToken.Heading -> {
						append(token.value)
					}

					is MarkdownToken.PlainTextSpan -> {
						append(token.value)
					}
				}
			}
		}

	SelectionContainer {
		Text(
			text = text,
		)
	}

}

@Preview
@Composable
private fun MarkdownPreview() {
	val markdown = """Hello, _*World*_ !
		|
		|What is _going_ on?
	""".trimMargin()

	Markdown(value = markdown)
}
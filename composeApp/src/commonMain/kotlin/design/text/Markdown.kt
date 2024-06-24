package design.text

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.sp
import design.type.jetBrainsMono
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownTokenTypes
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.getTextInNode
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Markdown(
    value: String,
    modifier: Modifier = Modifier,
) {
//    val lexer = MarkdownLexer()
//
//    val tokens = lexer.analyzeMarkdown(value)
//
//    val text =
//        buildAnnotatedString {
//
//            var boldIndex: Int? = null
//            var italicIndex: Int? = null
//
//            tokens.forEach { token ->
//                when (token) {
//                    is MarkdownToken.Emphasis -> {
//                        when (token.type) {
//                            MarkdownToken.Emphasis.Type.BOLD -> {
//                                if (boldIndex == null) {
//                                    boldIndex = pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
//                                } else {
//                                    pop(boldIndex!!)
//                                    boldIndex = null
//                                }
//                            }
//
//                            MarkdownToken.Emphasis.Type.ITALIC -> {
//                                if (italicIndex == null) {
//                                    italicIndex = pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
//                                } else {
//                                    pop(italicIndex!!)
//                                    italicIndex = null
//                                }
//                            }
//                        }
//                    }
//
//                    is MarkdownToken.Heading -> {
//                        append(token.value)
//                    }
//
//                    is MarkdownToken.PlainTextSpan -> {
//                        append(token.value)
//                    }
//
//                    is MarkdownToken.BasicBulletPoint -> {
//                        append(token.value)
//                    }
//
//                    is MarkdownToken.CheckboxBulletPoint -> {
//                        TODO()
//                    }
//
//                    is MarkdownToken.NumberedBulletPoint -> {
//                        TODO()
//                    }
//
//                    is MarkdownToken.CodeBlock -> {
//                        TODO()
//                    }
//
//                    is MarkdownToken.CodeSpan -> {
//                        TODO()
//                    }
//                }
//            }
//
//        }
//
//    SelectionContainer {
//        Text(
//            text = text,
//            modifier = modifier,
//        )
//    }

    IntelliJMarkdown(
        text = value,
        modifier = modifier,
    )

}

@Composable
private fun IntelliJMarkdown(
    text: String,
    modifier: Modifier = Modifier,
) {
    val flavor = CommonMarkFlavourDescriptor()
    val parser = MarkdownParser(flavour = flavor)
    val parsedTree = parser.buildMarkdownTreeFromString(text = text)

    val header1SpanStyle = SpanStyle(
        fontSize = 40.sp,
        fontWeight = FontWeight.ExtraBold,
    )


    val header2SpanStyle = SpanStyle(
        fontSize = 35.sp,
        fontWeight = FontWeight.ExtraBold,
    )

    val header3SpanStyle = SpanStyle(
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
    )

    val header4SpanStyle = SpanStyle(
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
    )

    val header5SpanStyle = SpanStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
    )

    val header6SpanStyle = SpanStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
    )

    val codeFontFamily = jetBrainsMono

    fun AnnotatedString.Builder.buildNode(node: ASTNode) {
        when (node.type) {
            MarkdownElementTypes.MARKDOWN_FILE -> {
                node.children.forEach { buildNode(it) }
            }

            MarkdownElementTypes.ATX_1 -> {
                val textSpan = node.getTextInNode(text)

                val computedString = textSpan
                    .drop(2)

                withStyle(header1SpanStyle) {
                    append(computedString)
                }

            }

            MarkdownElementTypes.ATX_2 -> {
                val textSpan = node.getTextInNode(text)

                val computedString = textSpan
                    .drop(3)

                withStyle(header2SpanStyle) {
                    append(computedString)
                }

            }

            MarkdownElementTypes.ATX_3 -> {
                val textSpan = node.getTextInNode(text)

                val computedString = textSpan
                    .drop(4)

                withStyle(header3SpanStyle) {
                    append(computedString)
                }

            }

            MarkdownElementTypes.ATX_4 -> {
                val textSpan = node.getTextInNode(text)

                val computedString = textSpan
                    .drop(5)

                withStyle(header4SpanStyle) {
                    append(computedString)
                }

            }

            MarkdownElementTypes.ATX_5 -> {
                val textSpan = node.getTextInNode(text)

                val computedString = textSpan
                    .drop(6)

                withStyle(header5SpanStyle) {
                    append(computedString)
                }

            }

            MarkdownElementTypes.ATX_6 -> {
                val textSpan = node.getTextInNode(text)

                val computedString = textSpan
                    .drop(7)

                withStyle(header6SpanStyle) {
                    append(computedString)
                }

            }

            MarkdownElementTypes.PARAGRAPH -> {
                node.children.forEach { buildNode(it) }
            }

            MarkdownElementTypes.EMPH -> {
                val textSpan = node.getTextInNode(text)

                val computedString = textSpan
                    .drop(1)
                    .dropLast(1)
                    .let {
                        AnnotatedString(
                            text = it.toString(),
                            spanStyle = SpanStyle(fontWeight = FontWeight.Bold)
                        )
                    }
                append(computedString)
            }

            MarkdownElementTypes.STRONG -> {
                val textSpan = node.getTextInNode(text)

                val computedString = textSpan
                    .drop(2)
                    .dropLast(2)
                    .let {
                        AnnotatedString(
                            text = it.toString(),
                            spanStyle = SpanStyle(fontStyle = FontStyle.Italic)
                        )
                    }
                append(computedString)
            }

            MarkdownElementTypes.CODE_SPAN -> {
                val textSpan = node.getTextInNode(text)

                val style = SpanStyle(
                    background = Color.LightGray,
                    fontFamily = codeFontFamily,
                    color = Color.Black,
                )

                val computedString = textSpan
                    .drop(1)
                    .dropLast(1)

                withStyle(style) {
                    append(computedString)
                }

            }

            MarkdownElementTypes.CODE_FENCE -> {
                val textSpan = node
                    .getTextInNode(text)
                    .lines()
                    .drop(1)
                    .dropLast(1)
                    .joinToString(separator = "\n")

                val paragraphStyle = ParagraphStyle(
                    textIndent = TextIndent(firstLine = 8.sp, restLine = 8.sp),
                )

                val style = SpanStyle(
                    background = Color.LightGray,
                    fontFamily = codeFontFamily,
                    color = Color.Black,
                )

                withStyle(paragraphStyle) {
                    withStyle(style) {
                        append(textSpan)
                    }
                }

            }

            MarkdownElementTypes.CODE_BLOCK -> {
                val textSpan = node
                    .getTextInNode(text)
                    .lines()
                    .drop(1)
                    .dropLast(1)
                    .joinToString(separator = "\n")

                val paragraphStyle = ParagraphStyle(
                    textIndent = TextIndent(firstLine = 8.sp, restLine = 8.sp),
                )

                val style = SpanStyle(
                    background = Color.LightGray,
                    fontFamily = codeFontFamily,
                    color = Color.Black,
                )

                withStyle(paragraphStyle) {
                    withStyle(style) {
                        append(textSpan)
                    }
                }

            }

            MarkdownTokenTypes.TEXT -> {
                append(node.getTextInNode(text))
            }

            MarkdownTokenTypes.WHITE_SPACE -> {
                append(node.getTextInNode(text))
                node.children.forEach { buildNode(it) }
            }

            else -> {
                append(node.getTextInNode(text))
                println("Type: ${node.type}\n\tText: ${node.getTextInNode(text)}")
            }
        }
    }

    val annotatedString =
        buildAnnotatedString {
            buildNode(parsedTree)
        }

    SelectionContainer(
        modifier = modifier,
    ) {
        Text(
            text = annotatedString,
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
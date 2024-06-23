package parsing.markdown.lexer

import parsing.Parser
import parsing.markdown.MarkdownParserState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class PlainTextMarkdownTextParserTest {

    @Test
    fun shouldParsePlainTextSpans() {
        // Arrange
        val text = "Plain Text Span"
        val positionEnd = text.length.dec()
        val expected =
            MarkdownToken.PlainTextSpan(
                value = text,
                positionStart = 0,
                positionEnd = positionEnd,
            )

        val input = MarkdownParserState(markdown = text)

        // Act
        val parser = PlainTextMarkdownTextParser()
        //Assert
        when (val result = parser(input = input)) {
            is Parser.Result.Failure -> {
                fail("Expected: $expected, Found: $result")
            }

            is Parser.Result.Match -> {
                assertEquals(expected, result.matchedItem)
            }
        }
    }

    @Test
    fun shouldStopAtSpecialCharacters() {
        // Arrange
        val texts = listOf(
            "01*3",
            "01_3",
            "01`3",
            "01-3",
        )
        val expected = MarkdownToken.PlainTextSpan(
            value = "01",
            positionStart = 0,
            positionEnd = 1,
        )

        val inputs = texts.map {
            MarkdownParserState(markdown = it)
        }

        // Act
        val parser = PlainTextMarkdownTextParser()
        val results = inputs.map(parser::invoke)

        //Assert
        results.forEach { result ->
            when (result) {
                is Parser.Result.Failure -> {
                    fail("Expected: $expected, Found: $result")
                }

                is Parser.Result.Match -> {
                    assertEquals(expected, result.matchedItem)
                }
            }
        }
    }

}
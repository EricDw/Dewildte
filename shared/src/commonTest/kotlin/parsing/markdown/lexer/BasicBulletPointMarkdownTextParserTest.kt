package parsing.markdown.lexer

import parsing.Parser
import parsing.markdown.MarkdownParserState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class BasicBulletPointMarkdownTextParserTest {

    @Test
    fun shouldParseSingleBasicBulletDelimiterCharacter() {
        // Arrange
        val text = "-1"
        val positionEnd = 0
        val expected =
            MarkdownToken.BasicBulletPoint(
                value = "-",
                positionStart = 0,
                positionEnd = positionEnd,
            )

        val input = MarkdownParserState(markdown = text)

        // Act
        val parser = BasicBulletPointMarkdownTextParser()
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

}
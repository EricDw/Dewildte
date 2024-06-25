package parsing.markdown.scanner

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TheMarkdownScanner {

    private lateinit var scanner: MarkdownScanner

    @BeforeTest
    fun setUp() {
        scanner = RealMarkdownScanner()
    }

    @Test
    fun shouldScanPlainTextTokens() {
        // Arrange
        val expected = 1
        val source = "Plain Text"

        // Act
        val actual = scanner.scan(source).size

        // Assert
        assertEquals(expected, actual)
    }

}
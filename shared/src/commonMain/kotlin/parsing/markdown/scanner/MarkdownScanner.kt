package parsing.markdown.scanner

import parsing.markdown.lexer.MarkdownToken

interface MarkdownScanner {
    fun scan(markdown: String): List<MarkdownToken>
}
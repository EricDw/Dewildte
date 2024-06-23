package parsing.markdown.lexer

import org.intellij.markdown.IElementType
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser
import kotlin.test.Test
import kotlin.test.assertEquals

class MarkdownLexerTest {

    @Test
    fun shouldAnalyzeValidMarkdown() {
        val lexer = MarkdownLexer()

        val markdown =
            """Plain Text Span
            |*Italics Span*
            |_Italics Span_
            |__Strong Span__
            |**Strong Span**""".trimMargin()

        val tokens =
            lexer.analyzeMarkdown(markdown)

        val conjoinedValues = tokens.joinToString("") { it.value }

        val log = buildString {
            appendLine("Tokens:")
            tokens.forEach(::appendLine)
            appendLine()
            appendLine(conjoinedValues)
        }

        print(log)

        assertEquals(
            markdown,
            conjoinedValues,
        )

    }


    @Test
    fun shouldProduceCommonMarkTree() {
        val source = """__Welcome__ to my slice of the internet!
(If you are reading this then my custom-built Markdown parser is working!)

I built this website for you.
It's a showcase of what can be built by a single person using Compose Multiplatform.
I will be populating the website with various bits and bobs.

Here's a checklist of things I hope to put here: 

- Support for Markdown
- Minimalist Markdown Editor
- Resume
- Math Trainer

Enjoy your stay and beware the bugs...it's software after all ;)""".trimMargin()

        val flavor = CommonMarkFlavourDescriptor()
        val parser = MarkdownParser(flavour = flavor)
        val parsedTree: ASTNode = parser.buildMarkdownTreeFromString(text = source)

        println()
        println()
        println(parsedTree)
        println()
        println()

        assertEquals(
            expected = parsedTree.type.name,
            actual = "MARKDOWN_FILE"
        )
    }
}
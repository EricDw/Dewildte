package parsing.markdown

import parsing.Parser

interface MarkdownTextParser<OUTPUT> : Parser<MarkdownParserState, OUTPUT, MarkdownParserError>
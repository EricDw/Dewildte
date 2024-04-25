package parsing.text

import parsing.Parser

interface TextParser<OUTPUT> : Parser<TextParserState, OUTPUT, TextParserError>

typealias TextParserResult<OUTPUT> = Parser.Result<TextParserState, OUTPUT, TextParserError>
package parsing.text

import parsing.Parser

interface TextParser<OUTPUT: Any, ERROR: Throwable> : Parser<TextParserState, OUTPUT, ERROR>

typealias TextParserResult<OUTPUT, ERROR> = Parser.Result<TextParserState, OUTPUT, ERROR>
package parsing.markdown.lexer

sealed interface MarkdownToken {

	val value: String
	
	val positionStart: Int
	
	val positionEnd: Int

	data class Heading(
		override val value: String,
		override val positionStart: Int,
		override val positionEnd: Int,
		val level: Heading.Level,
	) : MarkdownToken {
		
		enum class Level {
			ONE,
			TWO,
			THREE,
			FOUR,
			FIVE,
			SIX,
		}
		
	}

	data class Emphasis(
		override val value: String,
		override val positionStart: Int,
		override val positionEnd: Int,
		val type: Emphasis.Type,
	) : MarkdownToken {
		
		enum class Type {
			BOLD,
			ITALIC
		}
		
	}

	data class PlainTextSpan(
		override val value: String,
		override val positionStart: Int,
		override val positionEnd: Int,
	) : MarkdownToken

}
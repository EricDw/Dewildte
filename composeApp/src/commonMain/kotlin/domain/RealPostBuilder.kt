package domain

import androidx.compose.runtime.Composable
import kotlin.random.Random

class RealPostBuilder : Post.Builder {

	private val items = mutableListOf<PostItem>()

	override fun title(
		block: () -> String
	) {
		items += TitleItem(rawText = block())
	}

	override fun paragraph(
		block: () -> String
	) {
		items += ParagraphItem(rawText = block())
	}

	override fun composable(
		block: @Composable () -> Unit
	) {
		items += ComposableItem(code = block)
	}

	fun build(): Post {
		val randomString = Random.nextLong(
			from = 0,
			until = 40
		).toString()
		val postId = Post.Id(randomString)
		val postItems = items.toList()
		
		return Post(
			id = postId,
			items = postItems,
		)
	}
}
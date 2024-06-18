package presentation.posts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import design.text.Markdown
import design.text.Title
import domain.*
import org.jetbrains.compose.ui.tooling.preview.Preview

@Stable
class PostState(
	val post: Post
)

@Composable
fun Post(
	state: PostState,
	modifier: Modifier = Modifier,
) {
	LazyColumn(
		modifier = modifier,
		contentPadding = PaddingValues(16.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp),
	) {

		items(
			items = state.post.items,
		) { item ->
			when (item) {
				is ComposableItem -> {
					item.code()
				}

				is ParagraphItem -> {
					Markdown(value = item.text)
				}

				is TitleItem -> {
					Title(
						text = item.text,
						modifier = Modifier.fillMaxWidth(),
					)
					Spacer(Modifier.height(8.dp))
				}
			}
		}
	}

}

@Composable
fun Post(
	modifier: Modifier = Modifier,
	initializer: Post.Builder.() -> Unit,
) {
	val postState = remember(initializer) {
		val post = RealPostBuilder().apply(initializer).build()
		PostState(post = post)
	}

	Post(state = postState, modifier = modifier)

}

@Composable
@Preview
private fun PostPreview() {
	val post = post {
		title { """Title""" }

		paragraph { """Paragraph now with _bolding_!""" }

		composable {
			Button(onClick = {}) {
				Text("""Composable Button""")
			}
		}
	}
	val state = PostState(
		post = post,
	)

	Post(state = state)
}
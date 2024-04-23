package presentation.posts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import design.text.Paragraph
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
					Paragraph(text = item.text)
				}

				is TitleItem -> {
					Title(text = item.text)
					Spacer(Modifier.height(8.dp))
				}
			}
		}
	}

}

@Composable
@Preview
private fun PostPreview() {
	val post = post {
		title { """Title""" }

		paragraph { """Paragraph""" }

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
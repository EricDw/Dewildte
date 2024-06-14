package presentation.blog

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.posts.Post

@Composable
@Preview
fun BlogScreen() {
	val contentModifier = Modifier
		.fillMaxSize()

	Post(modifier = contentModifier) {
		paragraph {
			"""_TODO_""".trimMargin()
		}
	}
	
}
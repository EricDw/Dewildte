package presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun ProfileScreen() {
	val contentModifier = Modifier
		.fillMaxSize()

	Box(
		modifier = contentModifier,
		contentAlignment = Alignment.Center,
	) {
		val message = """Profile""".trimMargin()
		Text(
			text = message,
			style = MaterialTheme.typography.bodyMedium,
		)
	}
	
}
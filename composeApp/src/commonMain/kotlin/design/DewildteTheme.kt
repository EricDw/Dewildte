package design

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun DewildteTheme(
	content: @Composable () -> Unit
) {
	MaterialTheme(
		content = content
	)
}
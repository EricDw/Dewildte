package design.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Title(
	text: String,
	modifier: Modifier = Modifier,
) {
	Text(
		text = text,
		modifier = modifier,
		style = MaterialTheme.typography.titleLarge,
	)
}

@Composable
@Preview
private fun TitlePreview() {
	Title("Title Preview")
}
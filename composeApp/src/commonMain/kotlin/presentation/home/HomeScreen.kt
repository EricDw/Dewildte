package presentation.home

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
fun HomeScreen() {
	val contentModifier = Modifier
		.fillMaxSize()

	Box(
		modifier = contentModifier,
		contentAlignment = Alignment.Center,
	) {
		val message = """Hello, World!
			|
			|Welcome to my website!
			|
			|I am proud to say that I built this website using Kotlin Multilplatform 😎
			|The purpose of this website is to showcase my software development skills and host my personal blog.
			|
			|Thank you so much for visiting, I hope you have a fun time poking around.
			|
			|WARNING:
			|This place is a bit of a proving grounds for me and as such it is often using experiemental technology.
			|I apologise in advance for any bugs or glitches caused by any instability you find here.
		""".trimMargin()
		Text(
			text = message,
			style = MaterialTheme.typography.bodyMedium,
		)
	}
	
}
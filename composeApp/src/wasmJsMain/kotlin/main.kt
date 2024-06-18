import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import design.DewildteTheme
import kotlinx.browser.document
import presentation.WebApp

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
	ComposeViewport(document.body!!) {
		DewildteTheme {
			WebApp()
		}
	}
}
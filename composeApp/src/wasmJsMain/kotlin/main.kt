import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import design.DewildteTheme
import presentation.WebApp

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
	CanvasBasedWindow(canvasElementId = "ComposeTarget") {
		DewildteTheme {
			WebApp()
		}
	}
}